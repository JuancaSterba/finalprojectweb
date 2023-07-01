package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ProductDTO;
import com.itec1.finalprojectweb.entity.Product;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    private final IProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductServiceImpl(IProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO findOne(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return mapper.map(product, ProductDTO.class);
        } else {
            throw new NotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductDTO map = mapper.map(product, ProductDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) throws DataAccessException, InvalidDataException {
        validateDTO(productDTO);

        String description = productDTO.getDescription();
        Long sellerId = productDTO.getSellerId();

        // Verificar si ya existe un producto con la misma descripción y el mismo vendedor
        List<Product> existingProducts = productRepository.findByDescriptionAndSellerId(description, sellerId);
        if (!existingProducts.isEmpty()) {
            throw new InvalidDataException("Ya existe un producto con la misma descripción y el mismo vendedor");
        }

        Product product = mapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) throws DataAccessException, InvalidDataException {
        validateDTO(productDTO);

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Verificar si existen otros productos con los mismos datos a actualizar
            List<Product> existingProducts = productRepository.findByDescriptionAndSellerId(productDTO.getDescription(), productDTO.getSellerId());
            existingProducts.remove(product); // Excluir el producto actual de la lista

            if (!existingProducts.isEmpty()) {
                throw new InvalidDataException("Los datos a actualizar ya existen en otro producto");
            }

            product.setDescription(productDTO.getDescription());
            product.setHeight(productDTO.getHeight());
            product.setLength(productDTO.getLength());
            product.setWidth(productDTO.getWidth());
            product.setWeight(productDTO.getWeight());
            // Actualiza otros atributos según sea necesario

            Product updatedProduct = productRepository.save(product);
            return mapper.map(updatedProduct, ProductDTO.class);
        } else {
            throw new NotFoundException("Product not found with ID: " + id);
        }
    }

    public boolean validateDTO(ProductDTO productDTO) throws InvalidDataException {
        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            throw new InvalidDataException("Invalid product description");
        }
        if (productDTO.getHeight() <= 0 || productDTO.getLength() <= 0 || productDTO.getWidth() <= 0 || productDTO.getWeight() <= 0) {
            throw new InvalidDataException("Invalid product dimensions");
        }
        if (productDTO.getSellerId() == null) {
            throw new InvalidDataException("Invalid seller ID");
        }
        if (productDTO.getProductCategoryID() == null) {
            throw new InvalidDataException("Invalid category ID");
        }
        // Validar otros campos según sea necesario

        return true;
    }

    @Override
    public void deleteById(Long id) throws DataAccessException {
        try {
            productRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al borrar el producto", e) {};
        }
    }

    @Override
    public List<ProductDTO> findBySellerId(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductDTO map = mapper.map(product, ProductDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<ProductDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductDTO map = mapper.map(product, ProductDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<ProductDTO> findBySellerIdAndCategoryId(Long sellerId, Long categoryId) {
        List<Product> products = productRepository.findBySellerIdAndCategoryId(sellerId, categoryId);
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductDTO map = mapper.map(product, ProductDTO.class);
            list.add(map);
        }
        return list;
    }
}

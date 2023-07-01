package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ProductDTO;
import com.itec1.finalprojectweb.entity.Product;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
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

        Product product = mapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDTO.class);
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
        productRepository.deleteById(id);
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

    @Override
    public ProductDTO updateById(Long id, ProductDTO productDTO) throws DataAccessException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
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
}

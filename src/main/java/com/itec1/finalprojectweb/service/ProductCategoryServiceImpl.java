package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ProductCategoryDTO;
import com.itec1.finalprojectweb.entity.ProductCategory;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.IProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
    private final IProductCategoryRepository productCategoryRepository;
    private final ModelMapper mapper;

    public ProductCategoryServiceImpl(IProductCategoryRepository productCategoryRepository, ModelMapper mapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductCategoryDTO findOne(Long id) {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
        if (productCategoryOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            return mapper.map(productCategory, ProductCategoryDTO.class);
        } else {
            throw new NotFoundException("Product category not found with ID: " + id);
        }
    }

    @Override
    public List<ProductCategoryDTO> findAll() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return productCategories.stream()
                .map(productCategory -> mapper.map(productCategory, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO) throws DataAccessException, InvalidDataException {
        validateDTO(productCategoryDTO);

        ProductCategory existingCategory = productCategoryRepository.findByDescription(productCategoryDTO.getDescription());
        if (existingCategory != null) {
            throw new InvalidDataException("Product category with the same description already exists");
        }

        ProductCategory productCategory = mapper.map(productCategoryDTO, ProductCategory.class);
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        return mapper.map(savedCategory, ProductCategoryDTO.class);
    }

    @Override
    public ProductCategoryDTO update(ProductCategoryDTO productCategoryDTO, Long id) throws DataAccessException, InvalidDataException {
        validateDTO(productCategoryDTO);

        ProductCategory existingCategory = productCategoryRepository.findByDescription(productCategoryDTO.getDescription());
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            throw new InvalidDataException("Product category with the same description already exists");
        }

        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
        if (productCategoryOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            productCategory.setDescription(productCategoryDTO.getDescription());
            ProductCategory updatedCategory = productCategoryRepository.save(productCategory);
            return mapper.map(updatedCategory, ProductCategoryDTO.class);
        } else {
            throw new NotFoundException("Product category not found with ID: " + id);
        }
    }

    public boolean validateDTO(ProductCategoryDTO productCategoryDTO) throws InvalidDataException {
        String description = productCategoryDTO.getDescription();
        if (description == null || description.isEmpty()) {
            throw new InvalidDataException("Product category description cannot be empty");
        }
        return true;
    }

    @Override
    public void deleteById(Long id) throws DataAccessException {
        try {
            productCategoryRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al borrar la categoría de producto", e) {};
        }
    }

    @Override
    public ProductCategoryDTO findByDescription(String description) {
        ProductCategory productCategory = productCategoryRepository.findByDescription(description);
        if (productCategory == null) {
            return null;
        }
        return mapper.map(productCategory, ProductCategoryDTO.class);
    }
}

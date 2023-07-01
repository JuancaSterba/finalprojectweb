package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.SellerDTO;
import com.itec1.finalprojectweb.entity.Seller;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.ISellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements ISellerService {

    private final ISellerRepository sellerRepository;
    private final ModelMapper mapper;

    public SellerServiceImpl(ISellerRepository sellerRepository, ModelMapper mapper) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
    }

    @Override
    public SellerDTO findOne(Long id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            return mapper.map(seller, SellerDTO.class);
        } else {
            throw new NotFoundException("Seller not found with ID: " + id);
        }
    }

    @Override
    public List<SellerDTO> findAll() {
        List<Seller> sellers = sellerRepository.findAll();
        List<SellerDTO> list = new ArrayList<>();
        for (Seller seller : sellers) {
            SellerDTO map = mapper.map(seller, SellerDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public SellerDTO save(SellerDTO sellerDTO) throws DataAccessException, InvalidDataException {
        if (!validateDTO(sellerDTO)) {
            throw new DataAccessException("Invalid seller data") {};
        }

        Seller existingSeller = sellerRepository.findByCuit(sellerDTO.getCuit());
        if (existingSeller != null) {
            throw new DataAccessException("Seller already exists") {};
        }

        Seller seller = mapper.map(sellerDTO, Seller.class);
        Seller savedSeller = sellerRepository.save(seller);
        return mapper.map(savedSeller, SellerDTO.class);
    }

    @Override
    public boolean validateDTO(SellerDTO sellerDTO) throws InvalidDataException {
        if (sellerDTO.getName() == null || sellerDTO.getName().isEmpty()) {
            throw new InvalidDataException("Invalid seller name");
        }
        if (sellerDTO.getAddress() == null || sellerDTO.getAddress().isEmpty()) {
            throw new InvalidDataException("Invalid seller address");
        }
        if (sellerDTO.getCuit() == null || sellerDTO.getCuit().isEmpty()) {
            throw new InvalidDataException("Invalid seller CUIT");
        }
        return true;
    }

    @Override
    public void deleteById(Long id) throws DataAccessException {
        sellerRepository.deleteById(id);
    }

    @Override
    public SellerDTO findByCuit(String cuit) {
        Seller seller = sellerRepository.findByCuit(cuit);
        if (seller != null) {
            return mapper.map(seller, SellerDTO.class);
        } else {
            throw new NotFoundException("Seller not found with CUIT: " + cuit);
        }
    }

    @Override
    public SellerDTO updateById(SellerDTO sellerDTO, Long id) throws DataAccessException, InvalidDataException {
        if (!validateDTO(sellerDTO)) {
            throw new InvalidDataException("Invalid seller data");
        }

        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            // Actualizar los atributos del vendedor con los valores del DTO
            seller.setName(sellerDTO.getName());
            seller.setAddress(sellerDTO.getAddress());
            seller.setCuit(sellerDTO.getCuit());
            seller.setPhoneNumber(sellerDTO.getPhoneNumber());
            seller.setEmail(sellerDTO.getEmail());

            Seller updatedSeller = sellerRepository.save(seller);
            return mapper.map(updatedSeller, SellerDTO.class);
        } else {
            throw new NotFoundException("Seller not found with ID: " + id);
        }
    }
}

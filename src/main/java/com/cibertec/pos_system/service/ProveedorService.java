package com.cibertec.pos_system.service;

import com.cibertec.pos_system.entity.ProveedorEntity;
import com.cibertec.pos_system.repository.ProveedorRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorEntity> listar() {
        return proveedorRepository.findAll();
    }

    public Page<ProveedorEntity> listarPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return proveedorRepository.findAll(pageable);
    }

    public ProveedorEntity guardar(ProveedorEntity proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public ProveedorEntity actualizar(Long id, ProveedorEntity proveedor) {
        ProveedorEntity existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        proveedor.setActivo(existente.isActivo());
        proveedor.setId(id);

        return proveedorRepository.save(proveedor);
    }


    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }

    public Optional<ProveedorEntity> obtener(Long id) {
        return proveedorRepository.findById(id);
    }

     public ProveedorEntity obtenerProveedorPorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con RUC: " + ruc));
    }

    public void cambiarEstado(Long id, boolean activo) {
        proveedorRepository.findById(id).ifPresent(prov -> {
            prov.setActivo(activo);
            proveedorRepository.save(prov);
        });
    }

    public boolean buscarRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc).isPresent();
    }

    public boolean buscarRazonSocial(String ruc, String razonSocial) {
        return proveedorRepository.findByRucAndRazonSocial(ruc, razonSocial).isPresent();
    }

}

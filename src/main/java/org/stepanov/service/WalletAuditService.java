package org.stepanov.service;

import org.stepanov.entity.Audit;

import java.util.List;
import java.util.Optional;

public class WalletAuditService implements Service<Integer, Audit> {
    private static final WalletAuditService INSTANCE = new WalletAuditService();

    public static WalletAuditService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Audit> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Audit> findAll() {
        return null;
    }

    @Override
    public void save(Audit audit) {

    }

    @Override
    public void update(Audit audit) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}

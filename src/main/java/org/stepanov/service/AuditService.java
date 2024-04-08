package org.stepanov.service;

import org.stepanov.dao.AuditDao;
import org.stepanov.entity.Audit;

import java.util.List;
import java.util.Optional;

public class AuditService implements Service<Integer, Audit> {
    private static final AuditDao auditDao = AuditDao.getInstance();
    private static final AuditService INSTANCE = new AuditService();

    public static AuditService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Audit> findById(Integer id) {
        return auditDao.findById(id);
    }

    @Override
    public List<Audit> findAll() {
        return auditDao.findAll();
    }

    @Override
    public void save(Audit audit) {
        auditDao.save(audit);
    }

    @Override
    public void update(Audit audit) {
        auditDao.update(audit);
    }

    @Override
    public boolean delete(Integer id) {
        return auditDao.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return auditDao.deleteAll();
    }
}

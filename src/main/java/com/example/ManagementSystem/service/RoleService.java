package com.example.ManagementSystem.service;

import com.example.ManagementSystem.dto.CassandraPage;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class RoleService {
    static final String underfunded = "undefined";
    static final String Ordering = "asc";
    @Autowired
    private RoleRepository roleRepository;

    public Slice<Role> Paging(int init, int limit) {

        Slice<Role> firstBatch = roleRepository.findAll(CassandraPageRequest.of(0, limit));
        assertThat(firstBatch).hasSize(limit);
        return roleRepository.findAll(firstBatch.nextPageable());
    }

    public List<Role> findAll() {
        return roleRepository.findAll();

    }

    public Optional<Role> findById(UUID uuid) {
        return roleRepository.findById(uuid);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void destroy(UUID uuid) {
        roleRepository.deleteById(uuid);
    }

    public List<Role> getNextPage(String uuid, int limit) {

        if (uuid.isEmpty() || uuid.equals(underfunded)) {

            return roleRepository.getDefaultPage(limit);
        }
        return roleRepository.nextPage(UUID.fromString(uuid), limit);
    }

    public List<Role> getPreviousPage(String uuid, int limit) {
        if (uuid.isEmpty() || uuid.equals(underfunded)) return roleRepository.getDefaultPage(limit);
        List<Role> roleList = roleRepository.previousPage(UUID.fromString(uuid));
        List<Role> rolePage = new ArrayList<>();
        Collections.reverse(roleList);

        for (int i = 0; i < limit; i++) {
            if (i >= roleList.size()) break;
            rolePage.add(roleList.get(i));
        }
        Collections.reverse(rolePage);
        return rolePage;
    }

    public Long countRole() {
        return roleRepository.countRole();
    }
}

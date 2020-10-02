package com.example.ManagementSystem.service;

import com.example.ManagementSystem.model.Periods;
import com.example.ManagementSystem.repositories.PeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class PeriodsService {
    private static final int PAGE = 0;
    private static final String DEFAULT_CURSOR_MARK = "-1";
    private static final String SORT_FIELD = "test_name";
    @Autowired
    private PeriodsRepository periodsRepository;

    public Slice<Periods> paging(int pageSize, int cursorMark) {

        Slice<Periods> firstBatch = periodsRepository.findAll(CassandraPageRequest.first(pageSize));
        assertThat(firstBatch).hasSize(cursorMark);
        return periodsRepository.findAll(firstBatch.nextPageable());
    }

    public List<Periods> findAll() {
        return periodsRepository.findAll();
    }

    public Periods save(Periods periods) {
        return periodsRepository.save(periods);
    }

    public List<Periods> customPaging(int uuid, int limit) {
        return periodsRepository.page(uuid, limit);
    }
}
package com.example.ManagementSystem.service;

import com.example.ManagementSystem.dto.CassandraPage;
import com.example.ManagementSystem.model.Periods;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.repositories.PeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import com.datastax.driver.core.PagingState;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class PeriodsService {
    private static final int PAGE = 0;
    private static final String DEFAULT_CURSOR_MARK = "-1";
    private static final String SORT_FIELD = "test_name";
    @Autowired
    private PeriodsRepository periodsRepository;
    public Slice<Periods> paging(int pageSize, int limit) {
        Slice<Periods> firstBatch = periodsRepository.findAll(CassandraPageRequest.first(limit));
        assertThat(firstBatch).hasSize(limit);
       // return firstBatch;
        return periodsRepository.findAll(firstBatch.nextPageable());
    }

    public List<Periods> findAll() {
        return periodsRepository.findAll();
    }

    public Periods save(Periods periods) {
        return periodsRepository.save(periods);
    }

    public List<Periods> customPaging(int uuid, int limit) {
        if (uuid == 0) return periodsRepository.defaultPage(limit);
        List<Periods>periodsList = periodsRepository.page(uuid);
        List<Periods>periodsPage= new ArrayList<>();
        Collections.reverse(periodsList);
        for (int i = 0; i < limit; i++) {
            if(i>=periodsList.size())break;
            periodsPage.add(periodsList.get(i));
        }
        return periodsPage;
    }

    public List<Periods> getSort(int limit){
        List<Periods> periodsList = periodsRepository.defaultPage(limit);
       periodsList.sort(Comparator.comparing(Periods::getEvent_name));
        System.out.println(periodsList.toString());
       return periodsList;
    }
}

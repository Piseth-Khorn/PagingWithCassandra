package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.model.Periods;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.service.PeriodsService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SliderUI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/periods")
public class PeriodController {
    @Autowired
    private PeriodsService periodsService;

    @GetMapping
    public ResponseEntity<List<Periods>> findAll() {
        return ResponseEntity.ok(periodsService.findAll());
    }

    @PostMapping
    public ResponseEntity<List<Periods>> save(@RequestBody Periods periods) {
        int i = 0;
        List<Periods> roleList = new ArrayList<>();
        while (i < 10000) {
            String generatedString = RandomStringUtils.random(10, true, true);
            periods.setId(i);
            periods.setEvent_name(generatedString + i);
            periods.setPeriod_name(generatedString);
            periods.setStrong_race(i + generatedString);
            periods.setWeak_race(i + generatedString + i);
            roleList.add(periodsService.save(periods));
            i++;
        }
        return new ResponseEntity<>(roleList, HttpStatus.CREATED);
    }

//    @GetMapping("/page/{int}/{limit}")
//    public ResponseEntity<Slice<Periods>> pages(@PathVariable("int") int init, @PathVariable("limit") int limit) {
//        return new ResponseEntity<>(periodsService.paging(init, limit), HttpStatus.ACCEPTED);
//    }

    @GetMapping("/pages/{uuid}/{limit}")
    public ResponseEntity<List<Periods>> getPagingPeriods(@PathVariable("uuid") int uuid, @PathVariable("limit") int limit) {
        return new ResponseEntity<>(periodsService.customPaging(uuid, limit), HttpStatus.ACCEPTED);
    }

}

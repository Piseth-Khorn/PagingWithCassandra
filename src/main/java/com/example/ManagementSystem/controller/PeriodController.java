package com.example.ManagementSystem.controller;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.example.ManagementSystem.dto.CassandraPaging;
import com.example.ManagementSystem.model.Periods;
import com.example.ManagementSystem.service.PeriodsService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/periods")
public class PeriodController {
    @Autowired
    private PeriodsService periodsService;
    private final List<Periods> periodsList = new ArrayList<>();
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

    @GetMapping("/page/{int}/{limit}")
    public ResponseEntity<Slice<Periods>> pages(@PathVariable("int") int init, @PathVariable("limit") int limit) {
        return new ResponseEntity<>(periodsService.paging(init, limit), HttpStatus.ACCEPTED);
    }

    @GetMapping("/pages/{uuid}/{limit}")
    public ResponseEntity<List<Periods>> getPagingPeriods(@PathVariable("uuid") int uuid, @PathVariable("limit") int limit) {
        return new ResponseEntity<>(periodsService.customPaging(uuid, limit), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<Periods>> getSort(@PathVariable("limit") int limit) {
        return new ResponseEntity<>(periodsService.getSort(limit), HttpStatus.ACCEPTED);
    }

    @GetMapping("/dddd")
    public List<Periods> gg() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withoutJMXReporting().build();
        Session session = cluster.connect("managementsystem");
        String pageIndex = null;
        Response response = new Response();
        String query = "select * from role";
        Statement statement = new SimpleStatement(query).setFetchSize(5);
        if (!pageIndex.equals("0")) {
            statement.setPagingState(PagingState.fromString(pageIndex));
        }
        ResultSet rows = session.execute(statement);
        Integer numberOfRows = rows.getAvailableWithoutFetching();
        Iterator<Row> iterator = rows.iterator();
        while (numberOfRows-- != 0) {

        }
        return null;
    }

    @GetMapping("/g1/{limit}/{offset}")
    public ResponseEntity<List<Periods>> g1g(@PathVariable("limit") int limit, @PathVariable("offset") int offset) {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withoutJMXReporting().build();
        Session session = cluster.connect("managementsystem");
        Statement select = QueryBuilder.select().all().from("managementsystem", "periods");
        CassandraPaging cassandraPaging = new CassandraPaging(session);
        List<Row> firstRows = cassandraPaging.fetchRowsWithPage(select, offset, limit);
        for (Row row : firstRows) {
            periodsList.add(
                    new Periods(row.getInt("id"),
                    row.getString("period_name"),
                    row.getString("event_name"),
                    row.getTimestamp("event_data"),
                    row.getString("weak_race"),
                    row.getString("strong_race"))
            );
        }

        return new ResponseEntity<>(periodsList, HttpStatus.ACCEPTED);
    }

}

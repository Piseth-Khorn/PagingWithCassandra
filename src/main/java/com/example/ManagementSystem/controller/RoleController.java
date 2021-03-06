package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.dto.CassandraPageSet;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.service.RoleService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;

import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/role")
public class RoleController {
    private int aBoolean;

    {
        aBoolean = 0;
    }

    private List<CassandraPageSet> objectList = new ArrayList<>();
    private int currentIndex = 0;
    private Map<Integer, String> objectMap = new HashMap<Integer, String>();
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getALl() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable("id") UUID uuid) {
        Optional<Role> role = roleService.findById(uuid);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/page/{d}")
    public ResponseEntity<Slice<Role>> paging(@RequestParam(value = "pageSize", required = false) int limit, @RequestParam(value = "pageNumber", required = false) int init) {
        return new ResponseEntity<>(roleService.Paging(init, limit), HttpStatus.ACCEPTED);
    }

    @PostMapping("/d")
    public ResponseEntity<Role> save(@RequestParam("name") String name) {
        Role role = new Role();
        role.setId(Uuids.timeBased());
        role.setName(name);
        return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Role>> save(@RequestBody Role role) {
        try {
            int i = 1;
            List<Role> role1 = new ArrayList<>();
            int lenth = 10;
            while (i < 200000) {
                String genderatedString = RandomStringUtils.random(lenth, true, true);
                role.setId(Uuids.timeBased());
                role.setName(genderatedString);
                role1.add(roleService.save(role));
                i++;
            }
            return new ResponseEntity<>(role1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable("id") UUID uuid, @RequestBody Role role) {
        Optional<Role> role1 = roleService.findById(uuid);
        if (role1.isPresent()) {
            role.setId(role1.get().getId());
            return new ResponseEntity<>(roleService.save(role), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") UUID uuid) {
        try {
            roleService.destroy(uuid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getPagination(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "pageSize", required = false) int limit,
            @RequestParam(value = "condition", required = false) int condition,
            @RequestParam(value = "pageIndex", required = false) int pageIndex,
            @RequestParam("tokenId") String uuid) {
        // System.out.println(filter + "  " + sortOrder + "  " + limit + "  " + uuid + "   " + pageIndex);
        HashMap<String, Object> map = new HashMap<>();
        aBoolean = 1;
        if (pageIndex > currentIndex) {
            objectList.add(new CassandraPageSet(pageIndex, limit, uuid));
            System.out.println(objectList.toString());
        } else {
            System.out.println(objectList.size());
            if (!objectList.isEmpty() && objectList.size() > 1)
                uuid = objectList.get(objectList.size() - 2).getUuid();
            System.out.println(objectList.toString());
            for (CassandraPageSet cassandraPageSet : objectList) {
                if (limit * pageIndex == cassandraPageSet.getLimit() * cassandraPageSet.getPageIndex()) {
                    uuid = cassandraPageSet.getUuid();
                    aBoolean = 0;
                }

            }
        if (aBoolean == 1) {
            System.out.println("hi");
            if (!objectList.isEmpty())
                objectList.remove(objectList.size() - 1);

        }
            System.out.println(objectList.toString());
        }
        currentIndex = pageIndex;
        if (pageIndex == 0) {
            objectList.clear();
            uuid = "";
        }
        map.put("payload", roleService.getNextPage(uuid, limit));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> roleCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("rowCount", roleService.countRole());
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @GetMapping("/dd")
    public ResponseEntity<Slice<Role>> test() {
        Query query = Query.empty().pageRequest((CassandraPageRequest.first(10)));


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

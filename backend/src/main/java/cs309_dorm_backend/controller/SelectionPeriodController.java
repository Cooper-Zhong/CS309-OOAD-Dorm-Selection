package cs309_dorm_backend.controller;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.SelectionPeriod;
import cs309_dorm_backend.service.selectPeriod.SelectionPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selectionPeriod")
public class SelectionPeriodController {

    @Autowired
    private SelectionPeriodService selectionPeriodService;


    @GetMapping("/findAll")
    public List<SelectionPeriod> findAll() {
        return selectionPeriodService.findAll();
    }

    @GetMapping("/findById/{periodId}")
    public SelectionPeriod findById(@PathVariable Integer periodId) {
        return selectionPeriodService.findById(periodId);
    }

    @PostMapping("/update")
    public SelectionPeriod update(@RequestBody SelectionPeriod selectionPeriod) {
        return selectionPeriodService.update(selectionPeriod);
    }
}

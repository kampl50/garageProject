package com.example.demo.controllers;


import com.example.demo.models.CarPart;
import com.example.demo.models.Worker;
import com.example.demo.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkerAPI {

    @Autowired
    WorkerService workerService;


    @PostMapping("/api/worker/setPreferences")
    public void setWorkerExperienceAndPreferMark(@RequestParam String id, @RequestParam String experienceNumber, @RequestParam String preferMarkFirst,
                                                 @RequestParam String preferMarkSecond, @RequestParam String preferMarkThird) {

        workerService.setWorkerExperienceAndPreferMark(id, experienceNumber, preferMarkFirst, preferMarkSecond, preferMarkThird);
    }

    @PostMapping("/api/worker/editWorker")
    public void setWorkerExperienceAndPreferMark(@RequestBody Worker worker) {
        workerService.editWorker(worker);
    }

    @PostMapping("/api/worker/valuateOrder")
    public void valuateOrder(@RequestParam String id, @RequestParam String price, @RequestBody CarPart[] carParts) {
        workerService.priceTheOrder(id, price, carParts);
    }

    @PostMapping("/api/worker/setReadyOrder")
    public void setOrderLikeReady(@RequestParam String id){
        workerService.setOrderLikeReady(id);
    }

    @GetMapping("/api/worker/all")
    public List<Worker> getAll() {
        return workerService.getAll();
    }

    @GetMapping("/api/worker/byId")
    public Worker getWorkerById(@RequestParam String id) {
        return workerService.workerById(id);
    }
}

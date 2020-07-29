package com.example.demo.services;

import com.example.demo.enums.CarBrand;
import com.example.demo.enums.ISConfirmed;
import com.example.demo.enums.StatusNotification;
import com.example.demo.enums.WorkerStatus;
import com.example.demo.models.CarPart;
import com.example.demo.models.Notification;
import com.example.demo.models.Worker;
import com.example.demo.repositories.NotificationRepository;
import com.example.demo.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public String save(Worker worker) {
        workerRepository.save(worker);
        return workerRepository.findAll().get(workerRepository.findAll().size() - 1).getId();
    }

    public void editWorker(Worker updatedWorker) {
        workerRepository.deleteById(updatedWorker.getId());
        workerRepository.save(updatedWorker);
    }

    public void priceTheOrder(String id, String price, CarPart[] carParts) {
        Notification notification = notificationRepository.findById(id).get();

        notification.setPartsToOrder(Arrays.asList(carParts));
        notification.setPrice(Double.parseDouble(price));
        notification.setStatusNotification(StatusNotification.VALUATED);

        notificationRepository.deleteById(id);
        notificationRepository.save(notification);
    }

    public void isConfirmed(String id, String isConfirmed) {
        Notification notification = notificationRepository.findById(id).get();
        if (ISConfirmed.valueOf(isConfirmed).equals(ISConfirmed.YES)) {
            notification.setStatusNotification(StatusNotification.IN_PROGRESS);
            notificationRepository.save(notification);
        } else
            notificationRepository.deleteById(id);
    }

    private List<Worker> getWorkersWithoutNotifications() {
        return workerRepository.findAll().stream().filter(worker -> worker.getWorkerStatus().equals(WorkerStatus.WITHOUT_NOTIFICATIONS)).collect(Collectors.toList());
    }

    private List<Worker> getWorkersWithPreferBrand(CarBrand carBrand) {
        List<Worker> workersWithoutNotification = getWorkersWithoutNotifications();

        List<Worker> workersWithPreferBrands = workersWithoutNotification.stream()
                .filter(d -> d.getPreferCarBrands()
                        .parallelStream()
                        .anyMatch(f -> f.equals(carBrand)))
                .collect(Collectors.toList());

        if (workersWithPreferBrands.isEmpty())
            return workersWithoutNotification;
        else
            return workersWithPreferBrands;
    }

    private Worker getWorkerWithTheBiggestExperience(List<Worker> workers) {
        return workers.stream().max(Comparator.comparing(Worker::getExperience)).get();
    }

    public Notification addWorkerToNotification(Notification notification) {
        List<Worker> workersWithPreferBrand = getWorkersWithPreferBrand(CarBrand.valueOf(notification.getClientCar().getMark()));

        if (workersWithPreferBrand.isEmpty())
            return notification;

        Worker bestWorker = getWorkerWithTheBiggestExperience(workersWithPreferBrand);

        notification.getWorkersIds().add(bestWorker.getId());
        notification.getNameAndSurnameWorkers().add(bestWorker.getName() + " " + bestWorker.getSurname());
        bestWorker.setWorkerStatus(WorkerStatus.WITH_NOTIFICATIONS);
        bestWorker.setNumberOfNotifications(bestWorker.getNumberOfNotifications() + 1);
        workerRepository.deleteById(bestWorker.getId());
        workerRepository.save(bestWorker);

        return notification;
    }

    public void setWorkerExperienceAndPreferMark(String id, String experienceNumber, String preferMarkFirst, String preferMarkSecond, String preferMarkThird) {
        Worker worker = workerRepository.findById(id).get();

        worker.setExperience(Integer.parseInt(experienceNumber));
        worker.getPreferCarBrands().add(CarBrand.valueOf(preferMarkFirst));
        worker.getPreferCarBrands().add(CarBrand.valueOf(preferMarkSecond));
        worker.getPreferCarBrands().add(CarBrand.valueOf(preferMarkThird));

        workerRepository.deleteById(id);
        workerRepository.save(worker);
    }

    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    public Worker workerById(String id) {
        return workerRepository.findById(id).get();
    }

    public void setOrderLikeReady(String id) {
        Notification notification = notificationRepository.findById(id).get();
        notification.setStatusNotification(StatusNotification.READY);
        notificationRepository.save(notification);
    }
}

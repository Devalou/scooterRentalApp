package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateDockRequest;
import com.example.scooterRentalApp.api.response.CreateDockResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.RentHistory;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.ScooterDock;
import com.example.scooterRentalApp.repository.ScooterDockRepository;
import com.example.scooterRentalApp.repository.ScooterRentRepository;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.ScooterDockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.scooterRentalApp.common.ValidationUtils.isNullOrEmpty;
import static java.util.Objects.isNull;


@Service
public class ScooterDockServiceImpl extends AbstractCommonService implements ScooterDockService {

    private ScooterDockRepository scooterDockRepository;
    private ScooterRepository scooterRepository;
    private ScooterRentRepository scooterRentRepository;

    public ScooterDockServiceImpl(MsgSource msgSource, ScooterDockRepository scooterDockRepository, ScooterRepository scooterRepository, ScooterRentRepository scooterRentRepository) {
        super(msgSource);
        this.scooterDockRepository = scooterDockRepository;
        this.scooterRepository = scooterRepository;
        this.scooterRentRepository = scooterRentRepository;
    }

    @Override
    public ResponseEntity<Set<Scooter>> getAllDockScooters(Long scooterDockId) {
        ScooterDock scooterDock = extractScooterDockIfAvailable(scooterDockId);
        return ResponseEntity.ok(scooterDock.getScooters());
    }

    private ScooterDock extractScooterDockIfAvailable(Long scooterDockId) {
        Optional<ScooterDock> optionalScooterDock = scooterDockRepository.findById(scooterDockId);
        if (!optionalScooterDock.isPresent()) {
            throw new CommonConflictException(msgSource.ERR008);
        }
        return optionalScooterDock.get();
    }

    private Scooter extractScooterFromRepository(Long scooterId) {
        Optional<Scooter> optionalScooter = scooterRepository.findById(scooterId);
        if (!optionalScooter.isPresent()) {
            throw new CommonConflictException(msgSource.ERR010);
        }
        return optionalScooter.get();
    }

    private void checkScooterIsUnrented(Scooter scooter) {
        if (scooter.getScooterDock() != null || scooter.getUserAccount() == null) {
            throw new CommonConflictException(msgSource.ERR014);
        }
    }

    private void checkScooterInDock(Scooter scooter, Long scooterDockId) {
        if (scooter.getScooterDock() == null) {
            throw new CommonConflictException(msgSource.ERR017);
        }

        if (!scooter.getScooterDock().getId().equals(scooterDockId)) {
            throw new CommonConflictException(msgSource.ERR016);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> returnScooter(Long scooterDockId, Long scooterId) {
        ScooterDock scooterDock = extractScooterDockIfAvailable(scooterDockId);
        Scooter scooter = extractScooterFromRepository(scooterId);
        checkScooterIsUnrented(scooter);
        saveReturnToRentHistory(scooter);
        finalizeReturn(scooterDock, scooter);

        return ResponseEntity.ok(BasicResponse.of(msgSource.OK005));
    }

    private void saveReturnToRentHistory(Scooter scooter) {
        RentHistory rentHistory = scooterRentRepository.findFirstByUserAccountAndScooterOrderByIdDesc(scooter.getUserAccount(), scooter);
        rentHistory.setEndRent(LocalDateTime.now());
        scooterRentRepository.save(rentHistory);

    }

    @Override
    public ResponseEntity<BasicResponse> deleteScooter(Long scooterDockId, Long scooterId) {
        extractScooterDockIfAvailable(scooterDockId);
        Scooter scooter = extractScooterFromRepository(scooterId);
        checkScooterInDock(scooter, scooterDockId);
        scooter.setScooterDock(null);
        scooterRepository.save(scooter);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK008));
    }

    @Override
    public ResponseEntity<CreateDockResponse> createDock(CreateDockRequest request) {
        validateCreateDockRequest(request);
        checkScooterDockAlreadyExists(request.getDockName());
        ScooterDock addedDock = addDockToDataSource(request);
        return ResponseEntity.ok(new CreateDockResponse(msgSource.OK009, addedDock.getId()));

    }

    private ScooterDock addDockToDataSource(CreateDockRequest request) {
        ScooterDock scooterDock = new ScooterDock(
                null,
                request.getDockName(),
                request.getAvailablePlace()
        );
        return scooterDockRepository.save(scooterDock);

    }

    private void checkScooterDockAlreadyExists(String dockName) {
        List<ScooterDock> byDockName = scooterDockRepository.findByDockName(dockName);
        if (byDockName != null && byDockName.size() > 0) {
            throw new CommonConflictException(msgSource.ERR019);
        }
    }

    private void validateCreateDockRequest(CreateDockRequest request) {
        if (isNullOrEmpty(request.getDockName()) || isNull(request.getAvailablePlace())) {
            throw new CommonBadRequestException(msgSource.ERR001);
        }
        if (request.getAvailablePlace() < 5) {
            throw new CommonBadRequestException(msgSource.ERR018);
        }
    }

    private void finalizeReturn(ScooterDock scooterDock, Scooter scooter) {
        scooter.setScooterDock(scooterDock);
        scooter.setUserAccount(null);
        scooterRepository.save(scooter);
    }


}



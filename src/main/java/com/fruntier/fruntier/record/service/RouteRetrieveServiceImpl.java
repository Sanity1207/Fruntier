package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteRetrieveServiceImpl implements RouteRetrieveService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteRetrieveServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> listRoutesAllNormal() {
        List<Route> routes = routeRepository.findAll();
        if (routes == null) {
            return new ArrayList<>();
        }
        return routes;
    }

    @Override
    public Route getRouteFoundById(Long id) {
        Route route = routeRepository.findById(id).get();
        return route;
    }
}

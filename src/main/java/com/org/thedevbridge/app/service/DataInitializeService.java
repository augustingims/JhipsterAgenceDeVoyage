package com.org.thedevbridge.app.service;

import com.org.thedevbridge.app.domain.Bus;
import com.org.thedevbridge.app.domain.Chauffeur;
import com.org.thedevbridge.app.repository.BusRepository;
import com.org.thedevbridge.app.repository.ChauffeurRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DataInitializeService {

@Inject
private BusRepository busRepository;

@Inject
private ChauffeurRepository chauffeurRepository;

	@PostConstruct
	public void iniatilisation() throws FileNotFoundException, IOException{
		List<Bus> findAll = busRepository.findAll();
		if(findAll.isEmpty()){
			//initialize bus
			List<Bus> bus = new ArrayList<Bus>();
            bus.add(createBus("N° 100", "78ARD23"));
            bus.add(createBus("N° 101", "78ARD13"));
            bus.add(createBus("N° 102", "77ARD23"));
            bus.add(createBus("N° 103", "79ARD23"));
            bus.add(createBus("N° 104", "756RD23"));
            bus.add(createBus("N° 105", "7DCRD23"));
            bus.add(createBus("N° 106", "7DFPS23"));
            bus.add(createBus("N° 107", "78AHD23"));
            bus.add(createBus("N° 108", "78AND23"));
            bus.add(createBus("N° 109", "78AGD23"));

        }
        List<Chauffeur> find = chauffeurRepository.findAll();
		if(find.isEmpty()){
			//initialize chauffeur
			List<Chauffeur> chauffeur = new ArrayList<Chauffeur>();
            chauffeur.add(createChauffeur("Tokon", "Jean"));
            chauffeur.add(createChauffeur("Tokin", "Marc"));
            chauffeur.add(createChauffeur("Azerty", "Qwerty"));
            chauffeur.add(createChauffeur("Toto", "Marco"));
            chauffeur.add(createChauffeur("Tata", "Azerty"));

        }

	}

    public Bus createBus(String nom_bus, String immatriculation){
        Bus bus = new Bus(nom_bus, immatriculation);
        return busRepository.saveAndFlush(bus);
    }
    public Chauffeur createChauffeur(String nom_chauffeur, String prenom_chauffeur){
        Chauffeur chauffeur = new Chauffeur(nom_chauffeur, prenom_chauffeur);
        return chauffeurRepository.saveAndFlush(chauffeur);
    }

}

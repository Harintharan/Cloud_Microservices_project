package categary_microservice.example.categary.service.service.impl;

import categary_microservice.example.categary.service.dto.SaloonDTO;
import categary_microservice.example.categary.service.model.Categary;
import categary_microservice.example.categary.service.repository.CategaryRepository;
import categary_microservice.example.categary.service.service.CategaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class CategaryServiceImpl implements CategaryService {

private final CategaryRepository categaryRepository;
    @Override
    public Categary saveCategary(Categary categary, SaloonDTO saloonDTO) {

        Categary categary1 = new Categary();
        categary1.setName(categary.getName());
        categary1.setImage(categary.getImage());
        categary1.setSaloonId(saloonDTO.getId());


        return  categaryRepository.save(categary1);



    }

    @Override
    public Set<Categary> getAllCategariesBySaloonId(Long id) {
       return  categaryRepository.findBySaloonId(id);
    }

    @Override
    public Categary getCategaryById(Long id) throws Exception {

        Categary categary = categaryRepository.findById(id).orElse(null);
        if(categary == null)
        {
            throw  new Exception("categary not exist with id");

        }

        return  categary;
    }

    @Override
    public Categary deleteCategaryById(Long id, Long saloonId) throws Exception {
        Categary categary = getCategaryById(id);
        if (categary.getSaloonId().equals(saloonId)) {
            categaryRepository.deleteById(id);
            return categary;
        }

        throw new Exception("you dont have permision to delete this catagery");


    }

    @Override
    public Categary findByIdAndSaloonId(Long id, Long saloonId) throws Exception {
        Categary categary=categaryRepository.findByIdAndSaloonId(id,saloonId);

        if(categary == null)
        {
            throw new Exception("Catagary not found");

        }

        return categary;

        }
    }




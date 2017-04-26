package diploma.controller;

import diploma.model.Band;
import diploma.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IO on 21.04.2017.
 */
@RestController
@RequestMapping("/api/band")
public class BandController {

    private final BandRepository bandRepository;

    @Autowired
    public BandController(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Band> list(){
       return bandRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Band create(@RequestBody @Valid Band band){
        return bandRepository.save(band);
    }

    @RequestMapping(value = "/deleteArray", method = RequestMethod.POST)
    public void deleteArray(@RequestBody List<Band> bands){
        bandRepository.deleteInBatch(bands);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id){
        bandRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Band update(@RequestBody @Valid Band band){
        return bandRepository.save(band);
    }

    @RequestMapping(value = "/seek", method = RequestMethod.GET)
    public List<Band> seekBands(@PathParam("regex") String regex){
        return bandRepository.getAllByTitleStartingWithIgnoreCase(regex);
    }
}

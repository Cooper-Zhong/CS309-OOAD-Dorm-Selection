package backend.app;

import backend.domain.Building;
import backend.domain.Floor;
import backend.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //类控制器
@RequestMapping("/exer")
public class FloorApp {
    @Autowired
    private FloorService floorService;

    @GetMapping("/record")
    public List<Floor> findAll(){
        System.out.println(floorService.getClass().getName());
        return floorService.findAll();
    }

    @PostMapping("/record")
    public Floor addOne(Floor floor){
        return floorService.save(floor);
    }

    //use requestparam to update a line
    @PutMapping("/record")
    public Floor update(@RequestParam long id,
                           ){
        Floor floor=new Floor();
        floor.setId(id);
        return floorService.save(floor);
    }

    @DeleteMapping("record/{id}")
    public void deleteOne(@PathVariable long id){
        buildingService.deleteById(id);
    }

}

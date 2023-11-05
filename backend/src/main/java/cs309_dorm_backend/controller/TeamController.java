package cs309_dorm_backend.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.service.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/team")
@Api(tags = "Team Controller")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/findAll")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/findOne")
    public Team findOne(@RequestParam("teamId") int teamId) {
        return teamService.findOne(teamId);
    }

    @DeleteMapping("/deleteById/{teamId}")
    public boolean deleteById(@PathVariable int teamId) {
        return teamService.deleteById(teamId);
    }

    @PostMapping("/addOne")
    public Team addOne(@RequestBody Team team) {
        return teamService.addOne(team);
    }

    @PutMapping("/update")
    public Team update(@RequestBody Team team) {
        return teamService.update(team);
    }


}

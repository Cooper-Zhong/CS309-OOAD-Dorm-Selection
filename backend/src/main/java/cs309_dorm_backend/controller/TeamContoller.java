package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.TeamMemberDto;
import cs309_dorm_backend.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController //类控制器
@RequestMapping("/team") //请求映射
public class TeamContoller {
    @Autowired
    private TeamService teamService;

    @GetMapping("/findAll")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/findByCreator/{creatorId}")
    public Team findById(@PathVariable int creatorId) {
        return teamService.findByCreator(creatorId);
    }

    @PostMapping("/addTeam")
    public Team addTeam(@Valid @RequestBody Team team, BindingResult bindingResult) {
        return teamService.addTeam(team, bindingResult);
    }

    @PutMapping("/update") //
    public Team update(@RequestBody Team team) {
        return teamService.updateTeamName(team);
    }

    @PutMapping("/addMember")
    public Student addMember(@ Valid @RequestBody TeamMemberDto teamMemberDto, BindingResult bindingResult) {
        return teamService.addMember(teamMemberDto, bindingResult);
    }


    @DeleteMapping("/deleteByCreator/{creatorId}")
    public boolean deleteById(@PathVariable int creatorId) {
        return teamService.deleteByCreator(creatorId);
    }
}

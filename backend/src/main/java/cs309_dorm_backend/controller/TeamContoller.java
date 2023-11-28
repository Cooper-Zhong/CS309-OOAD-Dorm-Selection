package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.FavoriteDto;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.TeamMemberDto;
import cs309_dorm_backend.service.team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController //类控制器
@RequestMapping("/team") //请求映射
@Slf4j
public class TeamContoller {
    @Autowired
    private TeamService teamService;

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/findAll")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @GetMapping("/findByCreator/{creatorId}")
    public GlobalResponse findById(@PathVariable int creatorId) {
        Team team = teamService.findByCreator(creatorId);
        if (team == null) {
            return new GlobalResponse<>(1, "team not found", null);
        } else {
            return new GlobalResponse<>(0, "success", team);
        }
    }

    @PostMapping("/addTeam")
    public GlobalResponse addTeam(@Valid @RequestBody Team team, BindingResult bindingResult) {
        Team team1 = teamService.addTeam(team, bindingResult);
        if (team1 == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", team1);
        }
    }

    @PutMapping("/updateTeamName") // update team name
    public GlobalResponse updateTeamName(@RequestBody Team team) {
        Team team1 = teamService.updateTeamName(team);
        if (team1 == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", team1.getTeamName());
        }
    }

    @PutMapping("/updateTeamCreator") // update team creator
    public GlobalResponse updateTeamCreator(@RequestBody TeamMemberDto teamMemberDto) {
        Team team1 = teamService.updateTeamCreator(teamMemberDto);
        if (team1 == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", team1.getCreator());
        }
    }

    @PutMapping("/addMember")
    public GlobalResponse addMember(@Valid @RequestBody TeamMemberDto teamMemberDto, BindingResult bindingResult) {
        Student student = teamService.addMember(teamMemberDto, bindingResult);
        if (student == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", student);
        }
    }


    @DeleteMapping("/deleteByCreator/{creatorId}")
    public GlobalResponse deleteById(@PathVariable int creatorId) {
        boolean result = teamService.deleteByCreator(creatorId);
        if (result) {
            log.info("delete team created by " + creatorId);
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "team not found", null);
        }
    }

    @PostMapping("/favoriteRoom")
    public GlobalResponse favoriteRoom(@Valid @RequestBody FavoriteDto favoriteDto, BindingResult bindingResult) {
        boolean res = teamService.favoriteRoom(favoriteDto, bindingResult);
        if (!res) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", "favorite");
        }
    }

    @DeleteMapping("/unfavoriteRoom")
    public GlobalResponse unfavoriteRoom(@Valid @RequestBody FavoriteDto favoriteDto, BindingResult bindingResult) {
        boolean res = teamService.unfavoriteRoom(favoriteDto, bindingResult);
        if (!res) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", "unfavorite");
        }
    }
}

package cs309_dorm_backend.controller;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.*;
import cs309_dorm_backend.service.room.RoomService;
import cs309_dorm_backend.service.student.StudentService;
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
    @AntiReptile
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @AntiReptile
    @GetMapping("/findAll")
    public List<Team> findAll() {
        return teamService.findAll();
    }

    @AntiReptile
    @GetMapping("/findByCreator/{creatorId}")
    public GlobalResponse findById(@PathVariable String creatorId) {
        Team team = teamService.findByCreator(creatorId);
        if (team == null) {
            return new GlobalResponse<>(1, "team not found", null);
        } else {
            return new GlobalResponse<>(0, "success", team);
        }
    }

    @AntiReptile
    @PostMapping("/selectRoom")
    public GlobalResponse selectRoom(@RequestBody SelectDto selectDto) {
        Room room = teamService.selectRoom(selectDto);
        if (room == null) {
            return new GlobalResponse(1, "Room already been selected by other team!", null);
        } else {
            return new GlobalResponse<>(0, "Successfully selected!", room);
        }
    }

    @AntiReptile
    @PostMapping("/unselectRoom")
    public GlobalResponse unselectRoom(@RequestBody SelectDto selectDto) {
        Room room = teamService.unselectRoom(selectDto);
        if (room == null) {
            return new GlobalResponse<>(1, "Room already been selected by other team!", null);
        } else {
            return new GlobalResponse<>(0, "Successfully unselected!", room);
        }
    }

    @AntiReptile
    @GetMapping("/findSelectedRoom/{teamId}")
    public GlobalResponse findSelectedRoom(@PathVariable int teamId) {
        Room room = teamService.findSelectedRoom(teamId);
        if (room == null) {
            return new GlobalResponse(1, "not selected any room.", null);
        } else {
            return new GlobalResponse<>(0, "You have selected: ", room);
        }
    }

    @AntiReptile
    @GetMapping("/isInTeam/{studentId}")
    public GlobalResponse isInTeam(@PathVariable String studentId) {
        Team team = teamService.isInTeam(studentId);
        if (team == null) {
            return new GlobalResponse<>(1, studentId + " not in any team", null);
        } else {
            return new GlobalResponse<>(0, studentId + " is in a team", team);
        }
    }

    @AntiReptile
    @GetMapping("/swapRoom")
    public GlobalResponse swapRoom(@RequestParam int applyRoomId, @RequestParam int acceptRoomId) {
        teamService.swapRoom(applyRoomId, acceptRoomId);
        return new GlobalResponse<>(0, "swap room successfully", null);
    }

    @AntiReptile
    @GetMapping("/applySwap")
    public GlobalResponse applySwap(@RequestParam int applyCreatorId, @RequestParam int applyReceiverId) {
        teamService.applySwap(applyCreatorId, applyReceiverId);
        return new GlobalResponse<>(0, "apply swap successfully", null);
    }

    @AntiReptile
    @PostMapping("/addTeam")
    public GlobalResponse addTeam(@Valid @RequestBody Team team, BindingResult bindingResult) {
        Team team1 = teamService.addTeam(team, bindingResult);
        if (team1 == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", team1);
        }
    }

    @AntiReptile
    @PostMapping("/updateTeam") // update team name
    public GlobalResponse updateTeamName(@RequestBody Team team) {
        Team team1 = teamService.updateTeam(team);
        if (team1 == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", team1);
        }
    }

    @AntiReptile
    @PostMapping("/alterLeader") // update team creator
    public GlobalResponse updateTeamCreator(@RequestBody AlterLeaderDto alterLeaderDto) {
        Student newleader = teamService.alterLeader(alterLeaderDto);
        if (newleader == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "alter leader to " + newleader.getStudentId() + " successfully", newleader);
        }
    }

    @AntiReptile
    @PostMapping("/addMember")
    public GlobalResponse addMember(@Valid @RequestBody TeamMemberDto teamMemberDto, BindingResult bindingResult) {
        Student student = teamService.addMember(teamMemberDto, bindingResult);
        if (student == null) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", student);
        }
    }

    @AntiReptile
    @DeleteMapping("/deleteByCreator/{creatorId}")
    public GlobalResponse deleteTeamByCreator(@PathVariable String creatorId) {
        boolean result = teamService.deleteTeamByCreator(creatorId);
        if (result) {
            log.info("delete team created by " + creatorId);
            return new GlobalResponse<>(0, "delete team created by " + creatorId + " successfully", null);
        } else {
            return new GlobalResponse<>(1, "team not found", null);
        }
    }

    @AntiReptile
    @DeleteMapping("/deleteMember/{studentId}")
    public GlobalResponse deleteMember(@PathVariable String studentId) {
        boolean result = teamService.deleteMember(studentId);
        if (result) {
            log.info("delete student " + studentId + " from team");
            return new GlobalResponse<>(0, "delete student " + studentId + " from team successfully", null);
        } else {
            return new GlobalResponse<>(1, "delete failed.", null);
        }
    }

    @AntiReptile
    @PostMapping("/favoriteRoom")
    public GlobalResponse favoriteRoom(@Valid @RequestBody FavoriteDto favoriteDto, BindingResult bindingResult) {
        boolean res = teamService.favoriteRoom(favoriteDto, bindingResult);
        if (!res) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", "favorite");
        }
    }

    @AntiReptile
    @PostMapping("/unfavoriteRoom")
    public GlobalResponse unfavoriteRoom(@Valid @RequestBody FavoriteDto favoriteDto, BindingResult bindingResult) {
        boolean res = teamService.unfavoriteRoom(favoriteDto, bindingResult);
        if (!res) {
            return new GlobalResponse<>(1, "fail", null);
        } else {
            return new GlobalResponse<>(0, "success", "unfavorite");
        }
    }
}

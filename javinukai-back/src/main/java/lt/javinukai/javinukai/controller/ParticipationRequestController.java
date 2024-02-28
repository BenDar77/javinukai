package lt.javinukai.javinukai.controller;

import lt.javinukai.javinukai.entity.ParticipationRequest;
import lt.javinukai.javinukai.entity.User;
import lt.javinukai.javinukai.service.ParticipationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class ParticipationRequestController {
    private ParticipationRequestService participationRequestService;

    @Autowired
    public ParticipationRequestController(ParticipationRequestService participationRequestService) {
        this.participationRequestService = participationRequestService;
    }

    @GetMapping("/request")
    public List<ParticipationRequest> findAllRequests(){
        return participationRequestService.findAllRequests();
    }

    @PostMapping("/request")
    public ParticipationRequest create (ParticipationRequest participationRequest, @AuthenticationPrincipal User user){
        participationRequestService.createParticipationRequest(participationRequestService,);
        return participationRequest;
    }


}

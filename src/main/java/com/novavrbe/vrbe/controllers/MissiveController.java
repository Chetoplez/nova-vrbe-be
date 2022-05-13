package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.MissiveBusiness;
import com.novavrbe.vrbe.models.missivecontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missive")
public class MissiveController {

    @Autowired
    private MissiveBusiness missiveBusiness;

    @GetMapping("/getinbox/chId={chId}&inbox={inbox}")
    public ResponseEntity<GetMissiveResponse> getInbox (@PathVariable Integer chId , @PathVariable Boolean inbox){
        return missiveBusiness.getMissive(chId, inbox);
    }

    @PostMapping("/send")
    public ResponseEntity<SendMissiveResponse> sendMissiva(@RequestBody SendMissivaRequest request){
        return missiveBusiness.sendMissiva(request);
    }

    @PatchMapping("/read")
    public ResponseEntity<ReadMissivaResponse> readMissiva(@RequestBody ReadMissivaRequest request){
        return missiveBusiness.readMissive(request);
    }

    @PatchMapping("/delete")
    public ResponseEntity<DeleteMissiveResponse> deleteMissive (@RequestBody DeleteMissiveRequest request){
        return missiveBusiness.deleteMissive(request);
    }

    @GetMapping("/checkinbox/{chId}")
    public ResponseEntity<CheckInboxResponse> checkInbox (@PathVariable Integer chId) {
        return missiveBusiness.checkInbox(chId);
    }

}

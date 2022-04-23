package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.MissiveBusiness;
import com.novavrbe.vrbe.models.missivecontroller.GetMissiveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missive")
public class MissiveController {

    @Autowired
    private MissiveBusiness missiveBusiness;

    @GetMapping("/getinbox/{chId}")
    public ResponseEntity<GetMissiveResponse> getInbox (@PathVariable Integer chId){
        return missiveBusiness.getMissive(chId);
    }

    //@PostMapping("/send")
    //public ResponseEntity<SendMissiveResponse> sendMissiva(@RequestBody SendMissivaRequest request){
    //    return missiveBusiness.sendMissiva(request);
    //}
//
    //@DeleteMapping("/delete")
    //public ResponseEntity<DeleteMissiveResponse> deleteMissive (@RequestBody DeleteMissiveRequest request){
    //    return missiveBusiness.deleteMissive(request);
    //}
//
    //@GetMapping("/checkinbox/{chId}")
    //public ResponseEntity<CheckInboxResponse> checkInbox (@PathVariable String chId) {
    //    return missiveBusiness.checkInbox(chId);
    //}

}

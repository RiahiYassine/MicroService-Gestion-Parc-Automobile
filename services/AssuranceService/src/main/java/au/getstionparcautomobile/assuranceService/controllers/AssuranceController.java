package au.getstionparcautomobile.assuranceService.controllers;


import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import au.getstionparcautomobile.assuranceService.services.IAssuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assurances")
public class AssuranceController {

    private final IAssuranceService iAssuranceService;



    @PostMapping
    public ResponseEntity<AssuranceResponse> createAssurance(@RequestBody @Valid AssuranceRequest assuranceRequest){
        System.out.println("sup");
        AssuranceResponse assuranceResponse = this.iAssuranceService.createAssurance(assuranceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(assuranceResponse);
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> updateAssurance(@PathVariable Long id,@RequestBody @Valid AssuranceRequest assuranceRequest){
         this.iAssuranceService.updateAssurance(id,assuranceRequest);
         return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public void deleteAssuranceById(@PathVariable Long id){
        this.iAssuranceService.deleteAssurance(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<AssuranceResponse> getAssuranceById(@PathVariable Long id){
        AssuranceResponse assuranceResponse = this.iAssuranceService.getAssuranceById(id);
        return ResponseEntity.ok(assuranceResponse);
    }

}

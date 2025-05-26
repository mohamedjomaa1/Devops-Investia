package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.entities.Role;
import tn.esprit.investia.services.RoleService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Role")
public class RoleRestController {
    @Autowired
    RoleService roleService;


    @PostMapping("/add-Role")
    @ResponseBody
    public Role addRole(@RequestBody Role r)
    {
        return roleService.addRole(r);

    }
    @GetMapping
    public List<Role> getRole() {
        return roleService.getRole();
    }
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
    @PutMapping
    public Role updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }
}

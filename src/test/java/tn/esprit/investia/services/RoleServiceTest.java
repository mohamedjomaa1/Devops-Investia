package tn.esprit.investia.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import tn.esprit.investia.entities.Role;
import tn.esprit.investia.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void testAddRole_Success() {
        Role role = new Role();
        role.setName("ADMIN");
        when(roleRepository.save(role)).thenReturn(role);

        Role savedRole = roleService.addRole(role);

        assertNotNull(savedRole);
        assertEquals("ADMIN", savedRole.getName());
    }

    @Test
    void testGetRoles_ShouldReturnList() {
        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> roles = roleService.getRole();

        assertEquals(2, roles.size());
    }

    @Test
    void testDeleteRole_Success() {
        Long id = 1L;
        doNothing().when(roleRepository).deleteById(id);

        roleService.deleteRole(id);

        verify(roleRepository, times(1)).deleteById(id);
    }
}

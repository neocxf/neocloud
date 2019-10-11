package top.neospot.cloud.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.auth.entity.SysRole;
import top.neospot.cloud.auth.mapper.UserRoleMapper;
import top.neospot.cloud.auth.pojo.ResponseBo;
import top.neospot.cloud.auth.pojo.RoleDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/10/11.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    UserRoleMapper userRoleMapper;

    @GetMapping("/list")
    public ResponseBo showAllSysRoles() {
        List<SysRole> sysRoles = userRoleMapper.selectList(null);

        List<RoleDto> collect = sysRoles.stream().map(role -> new RoleDto().setId(role.getId()).setCode(role.getName()).setDes(role.getDescription()).setName(role.getName())).collect(Collectors.toList());

        return ResponseBo.ok(collect);
    }

    @PostMapping("/")
    public void saveRole(@RequestBody RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        sysRole.setName(roleDto.getName());
        sysRole.setDescription(roleDto.getDes());


        userRoleMapper.insert(sysRole);

    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") Long id) {
        userRoleMapper.deleteById(id);
    }
}

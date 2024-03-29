package fit.gja.songtrainer.service;

import fit.gja.songtrainer.dao.RoleDao;
import fit.gja.songtrainer.dao.UserDao;
import fit.gja.songtrainer.entity.Role;
import fit.gja.songtrainer.entity.User;
import fit.gja.songtrainer.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User service
 */
@Service
public class UserService implements UserDetailsService {

    // need to inject user dao
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userDao.findByUserName(userName);
    }

    @Transactional
    public void save(CrmUser crmUser) {
        User user = new User();
        // assign user details to the user object
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());

        // give user default role of "user"
        user.setRoles(Collections.singletonList(roleDao.findRoleByName("ROLE_USER")));
        // save user in the database
        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    public boolean checkIfValidOldPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }

    @Transactional
    public void removeLectorStudent(User student, User lector) {
        lector.getStudents().remove(student);
        student.getLectors().remove(lector);
        userDao.save(student);
        userDao.save(lector);
    }

    @Transactional
    public void addStudentLector(User student, User lector) {
        if(!(student.getLectors().contains(lector))) {
            student.getLectors().add(lector);
            userDao.save(student);
        }

        if(!(lector.getStudents().contains(student))) {
            lector.getStudents().add(student);
            userDao.save(lector);
        }
    }

    @Transactional
    public List<User> findUserByName(String keyword) {
        List<User> users = userDao.searchUserByName(keyword);
        return users;
    }
}
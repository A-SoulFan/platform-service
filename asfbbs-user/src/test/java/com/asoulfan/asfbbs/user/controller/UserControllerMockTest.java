package com.asoulfan.asfbbs.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.asoulfan.asfbbs.user.BaseMockTest;
import com.asoulfan.asfbbs.user.common.JwtComponent;
import com.asoulfan.asfbbs.user.domain.LoginToken;
import com.asoulfan.asfbbs.user.dto.LoginVo;
import com.asoulfan.asfbbs.user.dto.UserDto;
import com.asoulfan.asfbbs.user.service.ICaptService;
import com.asoulfan.asfbbs.user.service.IUserService;
import com.asoulfan.common.api.CommonResult;

/**
 * @author asuka
 * @date 2021/10/02
 */
@RunWith(PowerMockRunner.class)
public class UserControllerMockTest extends BaseMockTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private ICaptService captService;

    @Mock
    private IUserService userService;

    @Mock
    private JwtComponent jwtComponent;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    public void test_login_return_success_with_extra_info_result() {
        Mockito.when(captService.verify(anyString(), anyString())).thenReturn(true);
        UserDto userDto = random.nextObject(UserDto.class);
        Mockito.when(userService.queryUser(anyString())).thenReturn(userDto);
        Mockito.when(jwtComponent.generateJwt(any())).thenReturn(random.nextObject(String.class));
        Mockito.when(encoder.matches(any(), any())).thenReturn(true);

        CommonResult<LoginToken> result = userController.login(random.nextObject(LoginVo.class), null);

        assertThat(result).isNotNull();
    }
}

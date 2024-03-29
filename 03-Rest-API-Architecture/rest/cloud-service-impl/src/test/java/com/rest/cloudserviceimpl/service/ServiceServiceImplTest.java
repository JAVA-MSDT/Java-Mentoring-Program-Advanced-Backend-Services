package com.rest.cloudserviceimpl.service;

import java.util.List;
import java.util.Optional;

import com.rest.cloudserviceimpl.testutil.SubscriptionUtil;
import com.rest.cloudserviceimpl.testutil.UserUtil;
import com.rest.dto.converter.SubscriptionConverter;
import com.rest.dto.converter.SubscriptionResponseConverter;
import com.rest.dto.dto.SubscriptionRequestDto;
import com.rest.dto.dto.SubscriptionResponseDto;
import com.rest.dto.model.Subscription;
import com.rest.dto.model.User;
import com.rest.servicedb.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @InjectMocks
    private ServiceServiceImpl serviceService;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private UserServiceImpl userService;

    @Mock
    private SubscriptionConverter subscriptionConverter;

    @Mock
    private SubscriptionResponseConverter subscriptionResponseConverter;

    @Test
    void getAllSubscription() {
        // Given
        Subscription subscription = SubscriptionUtil.subscription();
        SubscriptionResponseDto subscriptionResponseDto = SubscriptionUtil.subscriptionResponseDto();

        // When
        when(serviceRepository.findAll()).thenReturn(List.of(subscription));
        when(subscriptionResponseConverter.convert(any(Subscription.class))).thenReturn(subscriptionResponseDto);

        // Then
        List<SubscriptionResponseDto> subscriptionResponseDtos = serviceService.getAllSubscription();
        assertEquals(subscriptionResponseDtos.size(), 1);
    }

    @Test
    void getSubscription_success() {
        // Given
        Subscription subscription = SubscriptionUtil.subscription();
        SubscriptionResponseDto subscriptionResponseDto = SubscriptionUtil.subscriptionResponseDto();

        // When
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(subscriptionResponseConverter.convert(any(Subscription.class))).thenReturn(subscriptionResponseDto);

        // Then
        SubscriptionResponseDto subscriptionResponseDtoDB = serviceService.getSubscription(SubscriptionUtil.ID);
        assertEquals(subscriptionResponseDtoDB.getUserId(), subscription.getUser().getId());
    }
    @Test
    void getSubscription_fail() {
        // Given
        // When
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            serviceService.getSubscription(SubscriptionUtil.ID);
        }, String.format(SubscriptionUtil.SUBSCRIPTION_NOT_FOUND_EXCEPTION_MESSAGE, UserUtil.ID));
    }

    @Test
    void createSubscription() {
        // Given
        Subscription subscription = SubscriptionUtil.subscription();
        SubscriptionResponseDto subscriptionResponseDto = SubscriptionUtil.subscriptionResponseDto();
        SubscriptionRequestDto subscriptionRequestDto = SubscriptionUtil.subscriptionRequestDto();
        User user = UserUtil.user();

        // When
        when(subscriptionConverter.convert(any(SubscriptionRequestDto.class), any(User.class))).thenReturn(subscription);
        when(userService.findUserById(anyLong())).thenReturn(user);
        when(serviceRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(subscriptionResponseConverter.convert(any(Subscription.class))).thenReturn(subscriptionResponseDto);

        // Then
        SubscriptionResponseDto subscriptionResponseDtoDB = serviceService.createSubscription(subscriptionRequestDto);
        assertEquals(subscriptionResponseDtoDB.getUserId(), subscription.getUser().getId());
    }


    @Test
    void updateSubscription() {
        // Given
        Subscription subscription = SubscriptionUtil.subscription();
        SubscriptionResponseDto subscriptionResponseDto = SubscriptionUtil.subscriptionResponseDto();
        SubscriptionRequestDto subscriptionRequestDto = SubscriptionUtil.subscriptionRequestDto();
        User user = UserUtil.user();

        // When
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(subscriptionConverter.convert(any(SubscriptionRequestDto.class), any(User.class))).thenReturn(subscription);
        when(userService.findUserById(anyLong())).thenReturn(user);
        when(serviceRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(subscriptionResponseConverter.convert(any(Subscription.class))).thenReturn(subscriptionResponseDto);

        // Then
        SubscriptionResponseDto subscriptionResponseDtoDB = serviceService.updateSubscription(subscriptionRequestDto);
        assertEquals(subscriptionResponseDtoDB.getUserId(), subscription.getUser().getId());
    }

    @Test
    void deleteSubscription_success() {
        // Given
        Subscription subscription = SubscriptionUtil.subscription();

        // When
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(subscription));

        // Then
        boolean subscriptionDeleteStatus = serviceService.deleteSubscription(SubscriptionUtil.ID);
        assertTrue(subscriptionDeleteStatus);
    }

    @Test
    void deleteSubscription_fail() {
        // Given
        // When
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            serviceService.deleteSubscription(SubscriptionUtil.ID);
        }, String.format(SubscriptionUtil.SUBSCRIPTION_NOT_FOUND_EXCEPTION_MESSAGE, UserUtil.ID));
    }
}

package ae.rta.dls.backend.client.rest.feign;

import ae.rta.dls.backend.web.rest.vm.ntf.SendSmsNotificationRequestVM;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value="notification",url="${gateway.context.root}", decode404 = true)
public interface NotificationProxy {

    /**
     * POST  /api/ntf/notification/send/sms : Send SMS message.
     *
     * @param sendSmsNotificationRequestVM the send Otp VM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notification, or with status
     * 400 (Bad Request) if the otpInformation has already an ID
     */
    @PostMapping("/notification/api/ntf/notification/send/sms")
    ResponseEntity<Void> sendSmsNotification(@Valid @RequestBody SendSmsNotificationRequestVM sendSmsNotificationRequestVM);
}

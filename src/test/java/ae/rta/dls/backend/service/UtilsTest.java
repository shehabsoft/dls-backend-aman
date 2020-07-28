package ae.rta.dls.backend.service;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.common.util.DataFormatterUtil;
import ae.rta.dls.backend.common.util.DateUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
@Transactional
public class UtilsTest {

    public static final String DATE_TIME_VALUE = "2014-11-12T05:50:00.0Z";

    @Test
    public void dateUtilTest() {
        Date date = new Date();
        Date newDate = DateUtil.addDays(date,1);
        assertThat(date).isBefore(newDate);

        // Date Formatting..
        assertThat(DateUtil.formatDate(Instant.parse(DATE_TIME_VALUE))).isEqualTo("12-11-2014");
        assertThat(DateUtil.formatDateTime(Instant.parse(DATE_TIME_VALUE))).isEqualTo("12-11-2014 09:50:00.0");
        assertThat(DateUtil.formatTime(Instant.parse(DATE_TIME_VALUE))).isEqualTo("09:50:00");
    }

    @Test
    public void dataFormatterTest() {
        assertThat(DataFormatterUtil.isValidUAEPhone("045255555")).isTrue();
        assertThat(DataFormatterUtil.isValidUAEPhone("04555555")).isFalse();

        assertThat(DataFormatterUtil.isValidUAEMobile("971506254444")).isTrue();
        assertThat(DataFormatterUtil.isValidUAEMobile("971596254444")).isFalse();
    }

    @Test
    public void numberUtilTest() {
        assertThat(NumberUtil.toLong("10")).isEqualTo(10);
    }
}

package br.com.alura.projeto.registration;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegistrationTest {

    @Test
    void create__should_set_userId_and_courseId_and_createdAt() {
        Registration registration = new Registration();
        registration.setUserId(1L);
        registration.setCourseId(2L);

        assertThat(registration.getUserId()).isEqualTo(1L);
        assertThat(registration.getCourseId()).isEqualTo(2L);
        assertThat(registration.getRegistrationDate()).isNotNull();
    }

    @Test
    void update__should_change_userId_and_courseId() {
        Registration registration = new Registration();
        registration.setUserId(1L);
        registration.setCourseId(2L);

        registration.setUserId(10L);
        registration.setCourseId(20L);

        assertThat(registration.getUserId()).isEqualTo(10L);
        assertThat(registration.getCourseId()).isEqualTo(20L);
    }

    @Test
    void createdAt__should_be_set_at_creation() {
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        Registration registration = new Registration();

        assertThat(registration.getRegistrationDate()).isAfterOrEqualTo(before);
    }

    @Test
    void userId_should_not_be_null_when_set() {
        Registration registration = new Registration();
        registration.setUserId(5L);

        assertThat(registration.getUserId()).isNotNull();
    }

    @Test
    void courseId_should_not_be_null_when_set() {
        Registration registration = new Registration();
        registration.setCourseId(7L);

        assertThat(registration.getCourseId()).isNotNull();
    }

    @Test
    void duplicate_registration_simulation() {
        Registration first = new Registration();
        first.setUserId(1L);
        first.setCourseId(1L);

        Registration second = new Registration();
        second.setUserId(1L);
        second.setCourseId(1L);

        assertThat(first.getUserId()).isEqualTo(second.getUserId());
        assertThat(first.getCourseId()).isEqualTo(second.getCourseId());
    }

    @Test
    void different_registrations_are_not_equal() {
        Registration first = new Registration();
        first.setUserId(1L);
        first.setCourseId(1L);

        Registration second = new Registration();
        second.setUserId(2L);
        second.setCourseId(2L);

        assertThat(first.getUserId()).isNotEqualTo(second.getUserId());
        assertThat(first.getCourseId()).isNotEqualTo(second.getCourseId());
    }

    @Test
    void createdAt_should_be_unique_for_each_instance() throws InterruptedException {
        Registration first = new Registration();
        Thread.sleep(5);
        Registration second = new Registration();

        assertThat(first.getRegistrationDate()).isNotEqualTo(second.getRegistrationDate());
    }
}

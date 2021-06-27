package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    int days;
    int weeks;

    public String dayOfMeeting(int days) {
        LocalDateTime today = LocalDateTime.now().plusDays(days);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String minDate = dateFormat.format(today);
        return minDate;
    }
    public String availableWeek(int weeks) {
        LocalDateTime now = LocalDateTime.now().plusWeeks(weeks);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String minWeek = dateFormat.format(now);
        return minWeek;
    }



    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestCorrectForm() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + dayOfMeeting(3)));

    }

    @Test
    void shouldTestWrongCity() {
        $("[data-test-id=city] .input__control").setValue("Урюпинск").click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $(".input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestWrongDate() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $("[data-test-id=date] [type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type='tel']").setValue(dayOfMeeting(1));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestWrongName() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Pylaeva Larisa");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWrongPhone() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("89219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotCheckBox() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $(Selectors.withText("Забронировать")).click();
        $("[role='presentation']").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestCityMenu() {
        $("[data-test-id=city] .input__control").setValue("Са");
        $$(".menu-item").find(Condition.exactText("Санкт-Петербург")).click();
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + dayOfMeeting(3)));
    }

    @Test
    void shouldTestCalendar() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург").click();
        $(".icon_name_calendar").click();
        $("[data-step='1']").click();
        $("[data-day='1625346000000']").click();
        $("[data-test-id=name] [type='text']").setValue("Пылаева Лариса");
        $("[data-test-id=phone] input").setValue("+79219503030");
        $("[data-test-id=agreement]").click();
        $(Selectors.withText("Забронировать")).click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + dayOfMeeting(7)));
    }
}
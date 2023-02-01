package fr.unice.polytech.progfonc;

public record Lst<T>(T car, Lst<T> cdr) {

}

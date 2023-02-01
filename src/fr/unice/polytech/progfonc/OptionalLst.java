package fr.unice.polytech.progfonc;

import java.util.Optional;

public record OptionalLst<T>(T car, Optional<OptionalLst<T>> cdr) {

}
package us.harvard.graphmytravels.data

// result generated from /json


data class Base(val name: String?, val topLevelDomain: List<String>?, val alpha2Code: String?, val alpha3Code: String?, val callingCodes: List<String>?, val capital: String?, val altSpellings: List<String>?, val region: String?, val subregion: String?, val population: Number?, val latlng: List<Number>?, val demonym: String?, val area: Number?, val gini: Any?, val timezones: List<String>?, val borders: List<Any>?, val nativeName: String?, val numericCode: String?, val currencies: List<Currencies>?, val languages: List<Languages>?, val translations: Translations?, val flag: String?, val regionalBlocs: List<Any>?, val cioc: String?)

data class Currencies(val code: String?, val name: String?, val symbol: String?)

data class Languages(val iso639_1: String?, val iso639_2: String?, val name: String?, val nativeName: String?)

data class Translations(val de: String?, val es: String?, val fr: String?, val ja: String?, val it: String?, val br: String?, val pt: String?, val nl: String?, val hr: String?, val fa: String?)


/*
// result generated from /json

data class Base1358182929(val name: String?, val topLevelDomain: List<String>?, val alpha2Code: String?, val alpha3Code: String?, val callingCodes: List<String>?, val capital: String?, val altSpellings: List<String>?, val region: String?, val subregion: String?, val population: Number?, val latlng: List<Number>?, val demonym: String?, val area: Number?, val gini: Number?, val timezones: List<String>?, val borders: List<String>?, val nativeName: String?, val numericCode: String?, val currencies: List<Currencies1562223479>?, val languages: List<Languages1518262342>?, val translations: Translations?, val flag: String?, val regionalBlocs: List<RegionalBlocs1490246178>?, val cioc: String?)

data class Currencies1562223479(val code: String?, val name: String?, val symbol: String?)

data class Languages1518262342(val iso639_1: String?, val iso639_2: String?, val name: String?, val nativeName: String?)

data class RegionalBlocs1490246178(val acronym: String?, val name: String?, val otherAcronyms: List<Any>?, val otherNames: List<String>?)

data class Translations(val de: String?, val es: String?, val fr: String?, val ja: String?, val it: String?, val br: String?, val pt: String?, val nl: String?, val hr: String?, val fa: String?)
*/
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Papers, Please is an indie video game where the player takes on a the role of a border crossing immigration officer
 * in the fictional dystopian Eastern Bloc-like country of Arstotzka in the year 1982.
 * As the officer, the player must review each immigrant and returning citizen's passports and other supporting paperwork
 * against a list of ever-increasing rules using a number of tools and guides, allowing in only those with the proper paperwork,
 * rejecting those without all proper forms, and at times detaining those with falsified information.
 *
 * https://www.codewars.com/kata/papers-please
 */
public class Inspector {

  // there are a total of 7 countries: Arstotzka, Antegria, Impor, Kolechia, Obristan, Republia, and United Federation.
  private Map<String, Boolean> isCountryAllowed = new HashMap<>();

  private Map<String, Set<String>> require = new HashMap<>();

  private static String RULE_ALLOW_CITIZENS = "^Allow citizens of (.+)$";
  private static String RULE_DENY_CITIZENS = "^Deny citizens of (.+)$";

  private static String RULE_WANTED_CRIMINAL = "^Wanted by the State: (.+)$";

  // regex(s) with two capturing blocks to capture keys and values
  private static String RULE_NOT_REQUIRE = "^(?:Citizens of )*(.*) no longer require (.+)$";
  private static String RULE_REQUIRE = "^(?:Citizens of )*(.*) require (.+)$";

  private String wantedCriminal;

  public Inspector() {
    isCountryAllowed.put("Arstotzka", false);
    isCountryAllowed.put("Antegria", false);
    isCountryAllowed.put("Impor", false);
    isCountryAllowed.put("Kolechia", false);
    isCountryAllowed.put("Obristan", false);
    isCountryAllowed.put("Republia", false);
    isCountryAllowed.put("United Federation", false);
  }

  public void receiveBulletin(String bulletin) {
    Arrays.stream(bulletin.split("\n")).forEach(this::update);
  }

  public void update(String rule) {
    if (rule.matches(RULE_ALLOW_CITIZENS)) {
      Arrays.stream(rule.replace("Allow citizens of ", "").split(", "))
          .forEach(c -> isCountryAllowed.put(c, true));
    }
    if (rule.matches(RULE_DENY_CITIZENS)) {
      Arrays.stream(rule.replace("Deny citizens of ", "").split(", "))
          .forEach(c -> isCountryAllowed.put(c, false));
    }
    if (rule.matches(RULE_NOT_REQUIRE)) {
      List<String> subjects = getKeys(rule, RULE_NOT_REQUIRE);
      List<String> docs     = getValues(rule, RULE_NOT_REQUIRE);
      for (String subject : subjects) {
        if (subject.equals("Entrants")) {
          require.forEach((k, v) -> v.removeAll(docs));
        }
        if (subject.equals("Foreigners")) {
          require.forEach((k, v) -> {
            if (!k.matches("Arstotzka|Entrants")) v.removeAll(docs);
          });
        }
        else {
          Set<String> docsRequiredBySubject = require.get(subject);
          if (docsRequiredBySubject != null)
            docsRequiredBySubject.removeAll(docs);
        }
      }
    }
    else if (rule.matches(RULE_REQUIRE)) {
      List<String> subjects = getKeys(rule, RULE_REQUIRE);
      List<String> docs     = getValues(rule, RULE_REQUIRE);
      for (String subject : subjects) {
        Set<String> docsRequiredBySubject = require.get(subject);
        if (docsRequiredBySubject == null)
          require.put(subject, new HashSet<>(docs));
        else
          docsRequiredBySubject.addAll(docs);
      }
    }
    if (rule.matches(RULE_WANTED_CRIMINAL)) {
      wantedCriminal = getRegexGroup(rule, RULE_WANTED_CRIMINAL, 1);
      wantedCriminal = wantedCriminal.split(" ")[1] + ", " + wantedCriminal.split(" ")[0];
    }
  }

  public String inspect(Map<String, String> person) {
    if (isWantedCriminal(person))
      return "Detainment: Entrant is a wanted criminal.";

    Optional<String> mismatch = getMismatch(person);
    if (mismatch.isPresent()) {
      return "Detainment: " + (formatKey(mismatch.get())) + " mismatch.";
    }

    // citizen of banned nation
    Optional<String> nation = getProp(person, "passport", "NATION");
    if (nation.isPresent() && !isCountryAllowed.get(nation.get())) {
      return "Entry denied: citizen of banned nation.";
    }

    List<String> identifiers = getIdentifiers(person);
    List<String> allRequired = identifiers.stream().map(require::get).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    List<String> requiredDocs = allRequired.stream().filter(r -> !r.endsWith("_vaccination")).collect(Collectors.toList());
    List<String> requiredVaccines = allRequired.stream().filter(r -> r.endsWith("_vaccination")).collect(Collectors.toList());

    // all required documents are present
    for (String doc : requiredDocs) {
      if (!person.containsKey(doc)) {
        // if entrant is a foreigner, a grant_of_asylum or diplomatic_authorization are acceptable in lieu of an access_permit
        // in the case where a diplomatic_authorization is used, it must include Arstotzka as one of the list of nations that can be accessed.
        if (doc.equals("access_permit") && identifiers.contains("Foreigners")) {
          if (person.containsKey("grant_of_asylum")) {
            Optional<String> expireDate = getProp(person, "grant_of_asylum", "EXP");
            if (expireDate.isPresent() && isExpired(expireDate.get()))
              return "Entry denied: grant of asylum expired.";
            continue;
          }
          if (person.containsKey("diplomatic_authorization")) {
            Optional<String> allowedNations = getProp(person, "diplomatic_authorization", "ACCESS");
            if (allowedNations.isPresent() && !Arrays.asList(allowedNations.get().split(", ")).contains("Arstotzka")) {
              return "Entry denied: invalid diplomatic authorization.";
            }
            else {
              Optional<String> expireDate = getProp(person, "diplomatic_authorization", "EXP");
              if (expireDate.isPresent() && isExpired(expireDate.get()))
                return "Entry denied: diplomatic authorization expired.";
            }
            continue;
          }
        }
        return "Entry denied: missing required " + formatKey(doc) + ".";
      }
      else {
        Optional<String> expireDate = getProp(person, doc, "EXP");
        if (expireDate.isPresent() && isExpired(expireDate.get()))
          return "Entry denied: " + formatKey(doc) + " expired.";
      }
    }

    for (String vaccine : requiredVaccines) {
      if (!person.containsKey("certificate_of_vaccination"))
        return "Entry denied: missing required certificate of vaccination.";

      String requiredVaccine = vaccine.replace("_vaccination", "");
      if (!isVaccinated(person, requiredVaccine))
        return "Entry denied: missing required vaccination.";
    }

    return (identifiers.contains("Arstotzka")) ? "Glory to Arstotzka." : "Cause no trouble.";
  }

  public static String getRegexGroup(String rule, String pattern, int group) {
    Matcher matcher = Pattern.compile(pattern).matcher(rule);
    return matcher.matches() ? matcher.group(group) : null;
  }

  public static List<String> getKeys(String rule, String pattern) {
    return Arrays.asList(getRegexGroup(rule, pattern, 1).split(", "));
  }

  public static List<String> getValues(String rule, String pattern) {
    return Arrays.asList(getRegexGroup(rule, pattern, 2).replace(" ", "_").split(", "));
  }

  /**
   * @vaccine string in the format measles, cholera, yellow_fever
   */
  private boolean isVaccinated(Map<String, String> person, String vaccine) {
    Optional<String> vaccines = getProp(person, "certificate_of_vaccination", "VACCINES");
    return vaccines.isPresent() && Arrays.asList(vaccines.get().split(", ")).contains(vaccine.replace("_", " "));
  }

  private Optional<String> getProp(Map<String,String> person, String doc, String prop) {
    String propValue = null;
    if (person.containsKey(doc))
      propValue = toMap(person.get(doc)).get(prop);
    return Optional.ofNullable(propValue);
  }

  private List<String> getIdentifiers(Map<String, String> person) {
    List<String> identifiers = new ArrayList<>(Collections.singletonList("Entrants"));
    Optional<String> nation = getProp(person, "passport", "NATION");
    if (nation.isPresent()) {
      identifiers.add(nation.get());
      if (!nation.get().equals("Arstotzka")) identifiers.add("Foreigners");
    }
    Optional<String> purpose = getProp(person, "access_permit", "PURPOSE");
    if (purpose.isPresent() && purpose.get().equals("WORK"))
      identifiers.add("Workers");

    return identifiers;
  }

  private Optional<String> getMismatch(Map<String, String> person) {
    Map<String, String> allInfo = new HashMap<>();
    List<String> ignore = Collections.singletonList("EXP");
    return person.entrySet().stream().map(Map.Entry::getValue).flatMap(s -> toMap(s).entrySet().stream())
        .filter(e -> {
          if (ignore.contains(e.getKey())) return false;
          String previousVal = allInfo.putIfAbsent(e.getKey(), e.getValue());
          return (previousVal != null && !previousVal.equals(e.getValue()));
        })
        .map(Map.Entry::getKey)
        .findFirst();
  }

  private boolean isWantedCriminal(Map<String, String> person) {
    if (wantedCriminal == null) return false;
    return person.entrySet().stream().flatMap(e -> toMap(e.getValue()).entrySet().stream())
        .anyMatch(e -> e.getKey().equals("NAME") && e.getValue().equals(wantedCriminal));
  }

  private static Map<String, String> toMap(String str) {
    Pattern pattern = Pattern.compile("(.+): (.+)");
    Map<String, String> result = new HashMap<>();
    Arrays.stream(str.split("\n")).map(pattern::matcher).filter(Matcher::matches)
        .forEach(m -> result.put(m.group(1), m.group(2)));
    return result;
  }

  /**
   * @return an LocalDate from a text string such as 2007.12.03.
   */
  private boolean isExpired(String expireDate) {
    if (expireDate == null) throw new IllegalArgumentException();
    return isExpired(LocalDate.parse(expireDate.replace(".", "-")));
  }

  private boolean isExpired(LocalDate expireDate) {
    return expireDate.isBefore(LocalDate.of(1982, 11, 23));
  }

  private String formatKey(String key) {
    if (key.equals("ID#")) return "ID number";
    if (key.equals("ID_card")) return "ID card";
    if (key.equals("NATION")) return "nationality";
    return key.toLowerCase().replace("_", " ");
  }
}

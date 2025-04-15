package guru.qa.model;

import java.util.List;

public class CharacterProfile {

    private String name;
    private String alias;
    private int age;
    private String origin;
    private String role;
    private List<Cyberware> cyberware;
    private List<Skill> skills;
    private String affiliation;
    private boolean wanted;
    private int bounty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Cyberware> getCyberware() {
        return cyberware;
    }

    public void setCyberware(List<Cyberware> cyberware) {
        this.cyberware = cyberware;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public boolean isWanted() {
        return wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        this.bounty = bounty;
    }
}

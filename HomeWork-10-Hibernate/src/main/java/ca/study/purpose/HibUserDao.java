package ca.study.purpose;

import java.util.Optional;

public interface HibUserDao<HibUser> {
    void create(HibUser obj);
    void update(HibUser obj);
    Optional<HibUser> load(long id, Class<HibUser> clazz);
}

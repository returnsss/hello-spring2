package hello2.hellospring2.repository;

import hello2.hellospring2.domain.Member;

import java.util.*;


/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    // Long : key, Member : value
    private static long sequence = 0L; // sequence는 키 값인 Long을 말함

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // null이 반환될 가능성이 있으면 Optional.ofNullable()로 감싼다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 루프를 돌려서 member의 name이 name이랑 같은지 필터링
                .findAny(); // 찾으면 반환함
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store의 Member들이 반환됨
    }

    public void clearStore() {
        store.clear();
    }
}

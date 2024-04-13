//package cz.muni.pa165.banking.application.repository;
//
//import cz.muni.pa165.banking.domain.balance.Balance;
//import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * @author Martin Mojzis
// */
//@Repository
//public class BalancesRepositoryImpl implements BalancesRepository  {
//
//    private final Map<String, Balance> mockData = new HashMap<>();
//
//    public BalancesRepositoryImpl() {
//        mockData.put("id1", new Balance("id1"));
//        mockData.put("id2", new Balance("id2"));
//    }
//
//    //@Transactional
//    @Override
//    public Optional<Balance> findById(String id) {
//        if (mockData.containsKey(id)) {
//            return Optional.of(mockData.get(id));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public List<String> getAllIds() {
//        return mockData.keySet().stream().toList();
//    }
//
//
//    //@Transactional
//    @Override
//    public void addBalance(String id) {
//        mockData.put(id, new Balance(id));
//    }
//}

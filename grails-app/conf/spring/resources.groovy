// Place your Spring DSL code here
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.jms.connection.SingleConnectionFactory
beans = {
//    jmsConnectionFactory(SingleConnectionFactory) {
//        targetConnectionFactory = { ActiveMQConnectionFactory cf ->
//            brokerURL = 'vm://localhost'
//        }
//    }
}

//jmsConnectionFactory(org.apache.activemq.ActiveMQConnectionFactory) {
//    brokerURL = 'vm://localhost'
//}
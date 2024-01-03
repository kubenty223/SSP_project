arp_handle 

while 1:  
  client_random_file_choice(random(1,4))  
  computer_http_request(srv_address, file)  
  srv_cpu_usage_generation(random(1,100)):  
    <ul>
    <li>controller requests servers periodically every 5s (using separate thread)</li>
    <li>(servers generate random numbers 0-100 and use flask to handle requests, add separate flow)</li>
    </ul>
  controller_chooses_srv(min(provided_numbers))
    <ul>
    <li>changes ip addr(.100 -> selected srv) </li>
      <ul><li>change ip</li>
      <li>send to port</li>
      <li>add reverse flow</li>
      <li>(replace mac in packets if needed)</li>
      </ul>
    </ul>
  flow_mod_sent_to_switch(switch_id,srv_ip,out_port_srv, idle_time >= 5s)
  flow_mod_sent_to_switch(switch_id,cli_ip,out_port_cli, idle_time = xs)
  srv_sends_file(cli_address, file)
    

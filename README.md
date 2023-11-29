# SSP_project
Discussed topology has been shown below:


![ssp](https://github.com/kubenty223/SSP_project/assets/56135959/13c0df6b-8bf9-4117-ab7f-f6cb27f18c0d)


Used literature and source of our topology:  
[Software Defined Network based Load Balancing](https://thesai.org/Downloads/Volume13No4/Paper_14-Software_Defined_Network_based_Load_Balancing.pdf)  
[Effectiveness of Implementing Load Balancing via SDN](https://interscity.org/assets/sdn_load_balancing_2019.pdf)

# Team members:  
Małgorzata Kosakowska  
Jakub Michalski  
Michał Kaszuba  
Sebastian Tlałka  
Michał Ptak

# How it works?
  # First our traffic generator
Using generate_files.py script we create three files of three different sizes on each of servers connected to the network. The idea is that the script will be run automatically during boot of each server.
Then, using python random() function, we create another script, this time on client machine. The script will randomly choose from which server the client will start downloading the file, which will also be chosen randomly.
This script will also be run during boot of each client and will also work in an infinite loop, so that the traffic will be generated all the time.

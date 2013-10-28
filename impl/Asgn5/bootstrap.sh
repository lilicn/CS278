#!/usr/bin/env bash

# update
sudo apt-get update

# install packages
sudo apt-get install openjdk-7-jdk -y
sudo apt-get install ant -y

# copy project
cp -a /vagrant/AndroidApp/ /home/vagrant/







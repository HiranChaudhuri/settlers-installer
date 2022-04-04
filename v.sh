#!/bin/bash -x

DATUM=`date +%Y%m%d-%H%M%S`
VERSION1=$(my_mvn exec:exec -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive -q | tail -1)
VERSION2="${VERSION1/%SNAPSHOT/$DATUM}"

#if [ "$VERSION1" != "$VERSION" ]
#then
#    my_mvn versions:set -DnewVersion=${VERSION2}
#fi

rm testbench/*.deb
time ./gradlew --info clean jpackage || exit 1

#if [ "$VERSION1" != "$VERSION" ]
#then
#    my_mvn versions:set -DnewVersion=${VERSION1}
#fi

echo Preparing Version ${VERSION2}

pushd .

cp app/build/settlersremake_*_amd64.deb testbench
cd testbench

time ansible-galaxy collection install community.general || exit 1
time vagrant plugin install vagrant-vbguest
time vagrant destroy -f || exit 1
time vagrant box update || exit 1
export VAGRANT_EXPERIMENTAL="disks"
time vagrant up || exit 1

# assume the last command did shutdown the box
sleep 70s
time vagrant halt

# remove shared directory
#VBoxManage sharedfolder remove OoliteDemo --name=vagrant
#VBoxManage modifyvm OoliteDemo --description="Oolite with Nexus v${VERSION2}"

#time vboxmanage export OoliteDemo --vsys 0 --output ~/OoliteDemo-${VERSION2}.ova --product "Nexus" --producturl "http://wiki.alioth.net/index.php/Nexus" --vendor "Hiran" --version ${VERSION} --description "OoliteDemo" || exit 1

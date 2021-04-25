#include "inotify/Inotify.h"
#include "inotify/FileSystemEvent.h"
#include <iostream>
#include <boost/filesystem.hpp>

int main(int argc, char** argv){
  if(argc <= 1){
    std::cout << "Usage: " << argv[0] << " /path/to/path/dir" << std::endl;
    exit(0);
  }
  // Directory to watch
  boost::filesystem::path dir(argv[1]);

  // Init inotify
  std::cout << "Setup watches for " << dir <<"..." << std::endl;
  Inotify inotify(IN_CREATE | IN_CLOSE_WRITE | IN_OPEN);
  
  // Watch a directory (plus all subdirectories and files)
  inotify.watchDirectoryRecursively(dir);

  inotify.ignoreFileOnce("file");
  
  // Wait for event of this directory
  std::cout << "Waiting for events..." << std::endl;
  while(true){
    FileSystemEvent event = inotify.getNextEvent();
    std::cout << "Event wd(" << event.wd << ") " << event.mask
              << "(" <<event.getMaskString() << ")"
              << " for " << event.path << " was triggered!" << std::endl;
  }
  
  return 0;
}

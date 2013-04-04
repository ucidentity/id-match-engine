package edu.berkeley.ucic.idmatch

class PlaceHolderController {
    edu.berkeley.ucic.idmatch.PlaceHolderService placeHolderService;
    def index() { render placeHolderService.println(params.message); }
}

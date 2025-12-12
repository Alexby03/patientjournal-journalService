package api.controllers;

import api.dto.*;
import core.enums.ConditionType;
import core.enums.LocationType;
import core.services.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientJournalController {

    @Inject
    PatientService patientService;

    @Inject
    PractitionerService practitionerService;

    @Inject
    OrganizationService organizationService;

    @Inject
    ObservationService observationService;

    @Inject
    LocationService locationService;

    @Inject
    EncounterService encounterService;

    @Inject
    ConditionService conditionService;

    // =======================
    // GET
    // =======================

    // Organizations =======================

    /** Get all organizations with pagination */
    @GET
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<OrganizationDTO> getAllOrganizations(@QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
                                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return organizationService.getAllOrganizations(pageIndex, pageSize);
    }

    /** Get organization by ID */
    @GET
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    @Path("/organizations/{organizationId}")
    public OrganizationDTO getOrganizationById(@PathParam("organizationId") UUID organizationId) {
        return organizationService.getOrganizationById(organizationId);
    }

    /** Get organizations by type */
    @GET
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    @Path("/organizations/type/{type}")
    public List<OrganizationDTO> getOrganizationsByType(@PathParam("type") String type) {
        return organizationService.getOrganizationsByType(core.enums.OrganizationType.valueOf(type));
    }

    /** Count total organizations */
    @GET
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    @Path("/organizations/count")
    public long countOrganizations() {
        return organizationService.countOrganizations();
    }

    // Locations =======================

    /** Get all locations */
    @GET
    @Path("/locations")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    /** Get location by ID */
    @GET
    @Path("/locations/{locationId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public LocationDTO getLocationById(@PathParam("locationId") UUID locationId) {
        return locationService.getLocationById(locationId);
    }

    /** Get locations by type */
    @GET
    @Path("/locations/type/{type}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public LocationDTO getLocationByType(@PathParam("type") LocationType type) {
        return locationService.getLocationByType(type);
    }

    /** Count total locations */
    @GET
    @Path("/locations/count")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public long countLocations() {
        return locationService.countLocations();
    }

    // Conditions =======================

    /** Get all conditions for a patient (optionally eager load) */
    @GET
    @Path("/conditions/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ConditionDTO> getPatientConditions(
            @PathParam("patientId") UUID patientId,
            @QueryParam("eager") @DefaultValue("false") boolean eager
    ) {
        return conditionService.getPatientConditions(patientId, eager);
    }

    /** Get all conditions a practitioner assigned (optionally eager load) */
    @GET
    @Path("/conditions/practitioner/{practitionerId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ConditionDTO> getPractitionerConditions(
            @PathParam("practitionerId") UUID practitionerId
    ) {
        return conditionService.getPractitionerConditions(practitionerId);
    }

    /** Get condition by ID */
    @GET
    @Path("/conditions/{conditionId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public ConditionDTO getConditionById(@PathParam("conditionId") UUID conditionId) {
        return conditionService.getConditionById(conditionId);
    }

    /** Get high severity conditions */
    @GET
    @Path("/conditions/high-severity")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ConditionDTO> getHighSeverityConditions() {
        return conditionService.getHighSeverityConditions();
    }

    /** Get conditions by type */
    @GET
    @Path("/conditions/type/{conditionType}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ConditionDTO> getConditionsByType(@PathParam("conditionType") ConditionType conditionType) {
        return conditionService.getConditionsByType(conditionType);
    }

    /** Count conditions for a patient */
    @GET
    @Path("/conditions/patient/{patientId}/count")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public long countPatientConditions(@PathParam("patientId") UUID patientId) {
        return conditionService.countPatientConditions(patientId);
    }

    // Encounters =======================

    /** Get all encounters for a patient (optionally eager) */
    @GET
    @Path("/encounters/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<EncounterDTO> getPatientEncounters(
            @PathParam("patientId") UUID patientId,
            @QueryParam("eager") @DefaultValue("false") boolean eager
    ) {
        return encounterService.getPatientEncounters(patientId, eager);
    }

    /** Get encounter by ID */
    @GET
    @Path("/encounters/{encounterId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public EncounterDTO getEncounterById(@PathParam("encounterId") UUID encounterId) {
        return encounterService.getEncounterById(encounterId);
    }

    /** Get recent encounters */
    @GET
    @Path("/encounters/recent")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<EncounterDTO> getRecentEncounters(@QueryParam("eager") @DefaultValue("false") boolean eager) {
        return encounterService.getRecentEncounters(eager);
    }

    /** Get all encounters for a practitioner */
    @GET
    @Path("/encounters/practitioner/{practitionerId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<EncounterDTO> getPractitionerEncounters(
            @PathParam("practitionerId") UUID practitionerId,
            @QueryParam("eager") @DefaultValue("false") boolean eager
    ) {
        return encounterService.getPractitionerEncounters(practitionerId, eager);
    }

    /** Count encounters for a patient */
    @GET
    @Path("/count/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public long countPatientEncounters(@PathParam("patientId") UUID patientId) {
        return encounterService.countPatientEncounters(patientId);
    }

    // Observations =======================

    /** Get all observations for a patient */
    @GET
    @Path("/observations/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ObservationDTO> getPatientObservations(@PathParam("patientId") UUID patientId) {
        return observationService.getPatientObservations(patientId);
    }

    /** Get observation by ID */
    @GET
    @Path("/observations/{observationId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public ObservationDTO getObservationById(@PathParam("observationId") UUID observationId) {
        return observationService.getObservationById(observationId);
    }

    /** Get most recent observation for a patient */
    @GET
    @Path("/observations/recent/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public ObservationDTO getMostRecentObservation(@PathParam("patientId") UUID patientId) {
        return observationService.getMostRecentObservation(patientId);
    }

    /** Get observations by practitioner */
    @GET
    @Path("/observations/practitioner/{practitionerId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public List<ObservationDTO> getPractitionerObservations(@PathParam("practitionerId") UUID practitionerId) {
        return observationService.getPractitionerObservations(practitionerId);
    }

    /** Count observations for a patient */
    @GET
    @Path("/observations/count/patient/{patientId}")
    @RolesAllowed({"Patient", "Doctor", "OtherStaff"})
    public long countPatientObservations(@PathParam("patientId") UUID patientId) {
        return observationService.countPatientObservations(patientId);
    }

    // =======================
    // POST
    // =======================

    // Organizations =======================

    /** Create a new organization */
    @POST
    @Path("/organizations")
    @Transactional
    @RolesAllowed({"Doctor"})
    public OrganizationDTO createOrganization(OrganizationDTO dto) {
        return organizationService.createOrganization(dto);
    }

    // Locations =======================

    /** Create a new location */
    @POST
    @Path("/locations")
    @Transactional
    @RolesAllowed({"Doctor"})
    public LocationDTO createLocation(LocationDTO dto) {
        return locationService.createLocation(dto);
    }

    // Conditions =======================

    /** Create new condition for patient and practitioner */
    @POST
    @Path("conditions/patient/{patientId}/practitioner/{practitionerId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public ConditionDTO createCondition(
            @PathParam("patientId") UUID patientId,
            @PathParam("practitionerId") UUID practitionerId,
            ConditionDTO dto
    ) {
        return conditionService.createCondition(patientId, practitionerId, dto);
    }

    // Encounters =======================

    /** Create new encounter */
    @POST
    @Path("encounters/patient/{patientId}/practitioner/{practitionerId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public EncounterDTO createEncounter(
            @PathParam("patientId") UUID patientId,
            @PathParam("practitionerId") UUID practitionerId,
            EncounterDTO dto
    ) {
        return encounterService.createEncounter(patientId, practitionerId, dto);
    }

    // Observations =======================

    /** Create a new observation */
    @POST
    @RolesAllowed({"Doctor", "OtherStaff"})
    @Path("/observations/patient/{patientId}/practitioner/{practitionerId}")
    @Transactional
    public ObservationDTO createObservation(@PathParam("patientId") UUID patientId,
                                            @PathParam("practitionerId") UUID practitionerId,
                                            ObservationDTO dto) {
        return observationService.createObservation(patientId, practitionerId, dto);
    }

    // =======================
    // PUT
    // =======================

    // Organizations =======================

    /** Update an existing organization */
    @PUT
    @Path("organizations/{organizationId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public OrganizationDTO updateOrganization(@PathParam("organizationId") UUID organizationId,
                                              OrganizationDTO dto) {
        return organizationService.updateOrganization(organizationId, dto);
    }

    // Locations =======================

    /** Update existing location */
    @PUT
    @Path("locations/{locationId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public LocationDTO updateLocation(@PathParam("locationId") UUID locationId,
                                      LocationDTO dto) {
        return locationService.updateLocation(locationId, dto);
    }

    // Conditions =======================

    /** Update existing condition */
    @PUT
    @Path("conditions/{conditionId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public ConditionDTO updateCondition(
            @PathParam("conditionId") UUID conditionId,
            ConditionDTO dto
    ) {
        return conditionService.updateCondition(conditionId, dto);
    }

    // Encounters =======================

    /** Update encounter */
    @PUT
    @Path("encounters/{encounterId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public EncounterDTO updateEncounter(
            @PathParam("encounterId") UUID encounterId,
            EncounterDTO dto
    ) {
        return encounterService.updateEncounter(encounterId, dto);
    }

    // Observations =======================

    /** Update an existing observation */
    @PUT
    @Path("/observations/{observationId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public ObservationDTO updateObservation(@PathParam("observationId") UUID observationId,
                                            ObservationDTO dto) {
        return observationService.updateObservation(observationId, dto);
    }

    // =======================
    // DELETE
    // =======================

    // Organizations =======================



    // Locations =======================

    /** Delete location */
    @DELETE
    @Path("/{locationId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public Response deleteLocation(@PathParam("locationId") UUID locationId) {
        boolean deleted = locationService.deleteLocation(locationId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Location not found")
                    .build();
        }
    }

    // Conditions =======================

    /** Delete condition by ID */
    @DELETE
    @RolesAllowed({"Doctor", "OtherStaff"})
    @Path("conditions/{conditionId}")
    @Transactional
    public Response deleteCondition(@PathParam("conditionId") UUID conditionId) {
        boolean deleted = conditionService.deleteCondition(conditionId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Condition not found")
                    .build();
        }
    }

    // Encounters =======================

    /** Delete encounter */
    @DELETE
    @RolesAllowed({"Doctor", "OtherStaff"})
    @Path("encounters/{encounterId}")
    @Transactional
    public Response deleteEncounter(@PathParam("encounterId") UUID encounterId) {
        boolean deleted = encounterService.deleteEncounter(encounterId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Encounter not found")
                    .build();
        }
    }

    // Observations =======================

    /** Delete an observation */
    @DELETE
    @Path("/observations/{observationId}")
    @Transactional
    @RolesAllowed({"Doctor", "OtherStaff"})
    public Response deleteObservation(@PathParam("observationId") UUID observationId) {
        boolean deleted = observationService.deleteObservation(observationId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Observation not found")
                    .build();
        }
    }

}
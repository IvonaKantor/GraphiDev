using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Car_controller : MonoBehaviour
{

    [SerializeField] private Transform _transformFL;
    [SerializeField] private Transform _transformFR;
    [SerializeField] private Transform _transformBL;
    [SerializeField] private Transform _transformBR;

    [SerializeField] private WheelCollider _colliderFL;
    [SerializeField] private WheelCollider _colliderFR;
    [SerializeField] private WheelCollider _colliderBL;
    [SerializeField] private WheelCollider _colliderBR;

    [SerializeField] private float _motorForce = 1000f;
    [SerializeField] private float _maxSteerAngle = 20f;
    [SerializeField] private float _brakeForce = 1000f;
    [SerializeField] private float _decelerationForce = 500f;
    [SerializeField] private float _steerSmoothness = 1f;

    private float currentSteerAngle;
    private float currentMotorForce;
    private bool isBraking;

    private void FixedUpdate()
    {
        HandleMotor();
        HandleSteering();
        UpdateWheels();
    }

    private void HandleMotor()
    {
        float verticalInput = Input.GetAxis("Vertical");

        if (verticalInput > 0)
        {
            currentMotorForce = verticalInput * _motorForce;
            isBraking = false;
        }
        else if (verticalInput < 0)
        {
            currentMotorForce = verticalInput * (_motorForce * 0.5f); 
            isBraking = false;
        }
        else
        {
            currentMotorForce = 0;
            ApplyDeceleration(_decelerationForce);
        }

        isBraking = Input.GetKey(KeyCode.Space);

        _colliderFL.motorTorque = currentMotorForce;
        _colliderFR.motorTorque = currentMotorForce;

        ApplyBraking();
    }

    private void ApplyBraking()
    {
        float brakeTorque = isBraking ? _brakeForce : 0f;
        _colliderFL.brakeTorque = brakeTorque;
        _colliderFR.brakeTorque = brakeTorque;
        _colliderBL.brakeTorque = brakeTorque;
        _colliderBR.brakeTorque = brakeTorque;
    }

    private void ApplyDeceleration(float force)
    {
        if (!isBraking && Mathf.Abs(GetComponent<Rigidbody>().linearVelocity.magnitude) > 0.1f)
        {
            _colliderFL.brakeTorque = force;
            _colliderFR.brakeTorque = force;
            _colliderBL.brakeTorque = force;
            _colliderBR.brakeTorque = force;
        }
        else
        {
            _colliderFL.brakeTorque = 0;
            _colliderFR.brakeTorque = 0;
            _colliderBL.brakeTorque = 0;
            _colliderBR.brakeTorque = 0;
        }
    }

    private void HandleSteering()
    {
        float horizontalInput = Input.GetAxis("Horizontal");
        float targetSteerAngle = _maxSteerAngle * horizontalInput;

        currentSteerAngle = Mathf.Lerp(currentSteerAngle, targetSteerAngle, Time.fixedDeltaTime * _steerSmoothness);

        _colliderFL.steerAngle = currentSteerAngle;
        _colliderFR.steerAngle = currentSteerAngle;
    }

    private void UpdateWheels()
    {
        UpdateWheel(_colliderFL, _transformFL);
        UpdateWheel(_colliderFR, _transformFR);
        UpdateWheel(_colliderBL, _transformBL);
        UpdateWheel(_colliderBR, _transformBR);
    }

    private void UpdateWheel(WheelCollider collider, Transform transform)
    {
        collider.GetWorldPose(out Vector3 position, out Quaternion rotation);
        transform.position = position;
        transform.rotation = rotation;
    }
}
